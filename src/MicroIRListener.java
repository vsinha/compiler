import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener {

    SymbolTableTree symbolTree;
    ParseTreeProperty<NodeProperties> ptp;

    // store all our created code nodes to this linked list
    public IRLinkedList ll; 

    // number of most recent temp register generated
    int registerNumber;

    // and of most recent label generated
    int labelNumber;
    
    public MicroIRListener(SymbolTableTree symbolTree) {
        this.symbolTree = symbolTree;
        this.ptp = new ParseTreeProperty<NodeProperties>();
        this.registerNumber = 0;
        this.labelNumber = 0;
        this.ll = new IRLinkedList();
    }
    
    private int getNewLabel() {
        labelNumber += 1;
        return getLabel();
    }

    private int getLabel() {
        return labelNumber;
    }

    public boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }

    private String getNewRegister(String type) {
        registerNumber += 1;
        String registerName = new String("$T" + registerNumber);
        symbolTree.addVariable(registerName, type);
        return registerName;
    }

    private String getRegister() {
        return new String("$T" + registerNumber);
    }

    private String lookupOpcode(String operator) {
        // we jump if the opposite of the desired
        // operator occurs, so we return the opposite
        // opcode
        switch (operator) {
            case ">":
                return "LE";
            case "<":
                return "GE";
            case ">=":
                return "LT";
            case "<=":
                return "GT";
            case "!=":
                return "EQ";
            case "=":
                return "NE";
            default:
                return "compareOpERROR";
        }
    }

    private String lookupOpcode(
            String operator, String type) {
        // look up the opcode for that var type
        if (operator.equals("+")) {
            if (type.equals("INT"))
                return "ADDI";
            if (type.equals("FLOAT"))
                return "ADDF";
        }

        if (operator.equals("-")) {
            if (type.equals("INT"))
                return "SUBI";
            if (type.equals("FLOAT"))
                return "SUBF";
        }

        if (operator.equals("*")) {
            if (type.equals("INT"))
                return "MULI";
            if (type.equals("FLOAT"))
                return "MULF";
        }

        if (operator.equals("/")) {
            if (type.equals("INT"))
                return "DIVI";
            if (type.equals("FLOAT"))
                return "DIFV";
        }
        return "ERROR";
    }

    public void addNodeProp(ParseTree ctx, 
            String key, String value) {
        NodeProperties np = ptp.get(ctx);
        
        System.out.println("adding node prop: " + key + ", " + value);

        // if we don't have the node props already, add it
        if ( ptp.get(ctx) == null ) {
            ptp.put(ctx, new NodeProperties(ctx.getText()));
        }

        ptp.get(ctx).data.put(key, value);
    }

    public void passToParent(ParserRuleContext ctx, String str) {
        ParserRuleContext parent = ctx.getParent();
        if (parent != null) {
            NodeProperties parentNodeProps = ptp.get(ctx.getParent());
            if (str != "null") {
                parentNodeProps.text = parentNodeProps.text + " " + str;
            }
        }
    }

    private void addNodeIfKeyExists(ParserRuleContext ctx, String key) {
        NodeProperties np = ptp.get(ctx);

        if (np.data.containsKey(key)) {
            ll.addNode(np.data.get(key));
        }
    }

    @Override public void exitStmt_list(
            MicroParser.Stmt_listContext ctx) {
        NodeProperties np = ptp.get(ctx);

        // do on_stmt_exit only under an IF, not an ELSE
        if (ctx.getParent().getChild(0).getText().equals("IF")) {
            addNodeIfKeyExists(ctx, "on_stmt_exit");
        }
    }
    

    @Override public void enterIf_stmt(
            MicroParser.If_stmtContext ctx) {
        //System.out.println(ctx.getText());
        symbolTree.enterScopeSequentially();

        // set the cond jump1 
        addNodeProp(ctx.getChild(2), "jump_label", "label" + getNewLabel());

        // exit stmt list jump2
        addNodeProp(ctx.getChild(5), "on_stmt_exit", 
                "JUMP label" + getNewLabel());

        // enter else_part label1
        addNodeProp(ctx.getChild(6), "on_else_enter", 
                "LABEL label" + (getLabel()-1));

        // exit else_part label2
        addNodeProp(ctx.getChild(6), "on_else_exit", 
                "LABEL label" + getLabel());
    }

    @Override public void exitIf_stmt(
            MicroParser.If_stmtContext ctx) {
        symbolTree.exitScope();
    }

    @Override public void enterElse_part(
            MicroParser.Else_partContext ctx) {
        addNodeIfKeyExists(ctx, "on_else_enter");
    }

    @Override public void exitElse_part(
            MicroParser.Else_partContext ctx) {
        addNodeIfKeyExists(ctx, "on_else_exit");
    }

    @Override public void enterWhile_stmt(
            MicroParser.While_stmtContext ctx) {
        symbolTree.enterScopeSequentially();

        // add new label1 to the linked list
        ll.addNode("LABEL label" + getNewLabel());

        // add jump to label2 to the end of the conditional
        // (so we failed the conditional)
        System.out.println("childtext: " + ctx.getChild(2).getText());
        addNodeProp(ctx.getChild(2), "jump_label", "label" + getNewLabel());

        // add jump to label1 to the endwhile
        addNodeProp(ctx, "endwhile_jump", "JUMP label" + (getLabel() - 1));

        // and label2 to the endwhile
        addNodeProp(ctx, "endwhile_label", "LABEL label" + getLabel());
    }

    @Override public void exitWhile_stmt(
            MicroParser.While_stmtContext ctx) {
        NodeProperties np = ptp.get(ctx);

        ll.addNode(np.data.get("endwhile_jump"));
        ll.addNode(np.data.get("endwhile_label"));

        symbolTree.exitScope();
    }

    @Override public void enterFunc_decl(
            MicroParser.Func_declContext ctx) {
        symbolTree.enterScopeSequentially();

        // label with the function name
        ll.addNode("LABEL " + ctx.getChild(2).getText());
        ll.addNode("LINK");
    }
    
    @Override public void exitFunc_decl(
            MicroParser.Func_declContext ctx) {
        symbolTree.exitScope();

        ll.addNode("RET");
    }

    @Override public void enterAssign_expr(
            MicroParser.Assign_exprContext ctx) {

        // create space (effectively a flag) for a 
        // value to assign to
        ptp.get(ctx).data.put("assign_Lvalue", null);
    }

    @Override public void exitAssign_expr(
            MicroParser.Assign_exprContext ctx) {
        System.out.println("exiting assign: " + ctx.getText());

        String Lvalue = ptp.get(ctx).data.get("assign_Lvalue");
        String storeOp = "ERROR";
        System.out.println(Lvalue);
        String LvalueType = symbolTree.lookup(Lvalue).type;

        if (LvalueType.equals("INT")) {
            storeOp = "STOREI";
        } else if (LvalueType.equals("FLOAT")) {
            storeOp = "STOREF";
        }

        ll.addNode(storeOp + " " + 
                   ptp.get(ctx).data.get("primary") + " " +
                   Lvalue);
    }

    @Override public void exitId(
            MicroParser.IdContext ctx) {
        NodeProperties parentNodeProps = ptp.get(ctx.getParent());
        System.out.println("here: " + ctx.getParent().getText());
        
        // if we're directly the child of an assign statement
        if (ctx.getParent().getChild(1) != null
                && ctx.getParent().getChild(1).getText().equals(":=")) {
              addNodeProp(ctx.getParent(), "assign_Lvalue", ctx.getText());
        }
    }

    @Override public void exitAddop(
            MicroParser.AddopContext ctx) {
        addNodeProp(ctx, "addop", ctx.getText()); 
    }

    @Override public void exitMulop(
            MicroParser.MulopContext ctx) {
        System.out.println("mulop: " + ctx.getText());
        addNodeProp(ctx, "mulop", ctx.getText()); 
    }

    @Override public void enterPrimary(
            MicroParser.PrimaryContext ctx) {
        System.out.println("entering primary: " + ctx.getText());
    }

    @Override public void exitPrimary(
            MicroParser.PrimaryContext ctx) {
        if (ctx.getChild(0).getText().equals("(")) {
            System.out.println("HERE");
            // our primary is a parenthesized expr [ie "(a + b)"]
            addNodeProp(ctx, "primary", 
                    ptp.get(ctx.getChild(1)).data.get("register"));
        } else {
            // pretend to have loaded it to a register
            if(isInteger(ctx.getText())) {
                //getNewRegister("INT");
            }
            addNodeProp(ctx, "primary", ctx.getText());
        }
    }

    @Override public void enterEveryRule(ParserRuleContext ctx){
        if (ctx.getText() != null && ptp.get(ctx) == null) {
           System.out.println("entering: " + ctx.getText());
           ptp.put(ctx, new NodeProperties(ctx.getText()));
        }

        ParserRuleContext parent = ctx.getParent();
        if (parent != null) {
            // grab the parent
            NodeProperties parentNodeProps = ptp.get(ctx.getParent());

            // first set primaries
            // smash all entries from parent onto the child
            ptp.get(ctx).data.putAll(parentNodeProps.data);

            // assign the updated child as the parent
            parentNodeProps.data = ptp.get(ctx).data;
        }
    }

    @Override public void exitEveryRule(ParserRuleContext ctx){
        // put all entries from the current node's hash table 
        // into the parent node
        // we don't want to clobber the parent node's entries,
        // so we do it backwards and reassign them

        ParserRuleContext parent = ctx.getParent();
        if (parent != null) {
            // grab the parent
            NodeProperties parentNodeProps = ptp.get(ctx.getParent());

            // first set primaries
            // smash all entries from child onto parent
            parentNodeProps.data.putAll(ptp.get(ctx).data);
        }
    }

    @Override public void enterFactor(
            MicroParser.FactorContext ctx) {
        System.out.println("entered factor");
    }

    @Override public void exitFactor(
            MicroParser.FactorContext ctx) {
        // check the expr_prefix which is an already-parsed 
        // child of the parent node
        System.out.println("exited factor");

        // check if we have a factor prefix with a mulop
        if ( !ctx.getChild(0).getText().isEmpty() ) { // mulop
            NodeProperties factor_prefix = ptp.get(ctx.getChild(0));
            if (!factor_prefix.getText().toString().isEmpty()) {
                // generate mulop IR
                String type = symbolTree.lookup(
                        ptp.get(ctx).data.get("primary")).type;
                String temp = getNewRegister(type);
                String opcode = lookupOpcode(
                        factor_prefix.data.get("mulop"), type);

                ll.addNode(opcode + " "
                        + factor_prefix.data.get("primary") + " " 
                        + ptp.get(ctx).data.get("primary") + " "
                        + temp
                        );

                addNodeProp(ctx, "primary", temp);
            }
        } else { // addop
            NodeProperties expr_prefix = ptp.get(ctx.getParent().getChild(0));

            if (!expr_prefix.getText().toString().isEmpty()) {
                // generate add IR
                String type = symbolTree.lookup(
                        ptp.get(ctx).data.get("primary")).type;
                String temp = getNewRegister(type);
                String opcode = lookupOpcode(
                        expr_prefix.data.get("addop"), type);
                System.out.println("opcode lookup: " + opcode);

                ll.addNode(opcode + " "
                        + expr_prefix.data.get("primary") + " "
                        + ptp.get(ctx).data.get("primary") + " "
                        + temp
                        );

                addNodeProp(ctx, "register", temp);
            }
        }
    }
     
    @Override public void exitFactor_prefix(
            MicroParser.Factor_prefixContext ctx) {
        // pass up the last register if it exists
        /*
        if (ptp.get(ctx).data.containsKey("register")) {
            System.out.println("here1");
          addNodeProp(ctx, "register", ptp.get(ctx).data.get("register"));
        } else {
            System.out.println("here2");
          addNodeProp(ctx, "register", ptp.get(ctx).data.get("primary"));
        }
        */
    }
    

    @Override public void exitExpr(MicroParser.ExprContext ctx) {
        NodeProperties np = ptp.get(ctx);

        // pass up the last register if it exists
        /*
        if (ptp.get(ctx).data.containsKey("register")) {
            System.out.println ("register -> primary");
          addNodeProp(ctx, "primary", ptp.get(ctx).data.get("register"));
        } else {
            System.out.println ("primary -> register");
          addNodeProp(ctx, "register", ptp.get(ctx).data.get("primary"));
        }
        */
    }
    
    @Override public void exitExpr_prefix(
            MicroParser.Expr_prefixContext ctx) {
        NodeProperties parentProps = ptp.get(ctx.getParent());
    }

    @Override public void exitCompop(MicroParser.CompopContext ctx) {
        addNodeProp(ctx, "compop", ctx.getText());
    }


    @Override public void enterCond(MicroParser.CondContext ctx) {
        NodeProperties np = ptp.get(ctx);
    }

    @Override public void exitCond(MicroParser.CondContext ctx) {
        NodeProperties np = ptp.get(ctx);
        String label;
        if (np.data.containsKey("jump_label")) {
            label = np.data.get("jump_label");
        } else {
            label = "label" + getNewLabel();
        }

        ll.addNode( 
          lookupOpcode(ptp.get(ctx.getChild(1)).data.get("compop")) + " " + 
          ptp.get(ctx.getChild(0)).data.get("register") + " " + 
          ptp.get(ctx.getChild(2)).data.get("register") + " " +
          label
        );
    }

    @Override public void enterId_list(MicroParser.Id_listContext ctx) {
        for (String id : ctx.ids){
            if(ctx.getParent().getChild(0).getText().equals("WRITE")){

                if (symbolTree.lookup(id).type.equals("INT")){
                    ll.addNode("WRITEI " + id);
                }else if (symbolTree.lookup(id).type.equals("FLOAT")) {
                    ll.addNode("WRITEF " + id);
                }else if (id.equals("newline")) {
                    ll.addNode("WRITES " + id);
                }
            }else if (ctx.getParent().getChild(0).getText().equals("READ")) {
                if (symbolTree.lookup(id).type.equals("INT")){
                    ll.addNode("READI " + id);
                }else if (symbolTree.lookup(id).type.equals("FLOAT")) {
                    ll.addNode("READF " + id);
                }
            }
        }
    }
}
