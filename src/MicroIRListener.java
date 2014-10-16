import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener {

    SymbolTableTree symbolTree;
    ParseTreeProperty<NodeProperties> ptp;

    // number of most recent temp register generated
    int registerNumber;
    // and of most recent label generated
    int labelNumber;
    
    public MicroIRListener(SymbolTableTree symbolTree) {
        this.symbolTree = symbolTree;
        this.ptp = new ParseTreeProperty<NodeProperties>();
        this.registerNumber = 0;
        this.labelNumber = 0;
        System.out.println();
    }
    
    private String getNewLabel() {
        labelNumber += 1;
        return getLabel();
    }

    private String getLabel() {
        return new String("label" + labelNumber);
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
        switch (operator) {
            case ">":
                return "GT";
            case "<":
                return "LT";
            case ">=":
                return "GE";
            case "<=":
                return "LE";
            case "!=":
                return "NE";
            case "=":
                return "EQ";
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

    public void addNodeProp(ParserRuleContext ctx, 
            String key, String value) {
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

    @Override public void enterIf_stmt(
            MicroParser.If_stmtContext ctx) {
        symbolTree.enterScopeSequentially();
    }

    @Override public void exitIf_stmt(
            MicroParser.If_stmtContext ctx) {
        symbolTree.exitScope();
    }

    @Override public void enterWhile_stmt(
            MicroParser.While_stmtContext ctx) {
        symbolTree.enterScopeSequentially();
    }

    @Override public void exitWhile_stmt(
            MicroParser.While_stmtContext ctx) {
        symbolTree.exitScope();
    }

    @Override public void enterFunc_decl(
            MicroParser.Func_declContext ctx) {
        symbolTree.enterScopeSequentially();
    }
    
    @Override public void exitFunc_decl(
            MicroParser.Func_declContext ctx) {
        symbolTree.exitScope();
    }

    @Override public void enterAssign_expr(
            MicroParser.Assign_exprContext ctx) {

        // create space (effectively a flag) for a 
        // value to assign to
        ptp.get(ctx).data.put("assign_Lvalue", null);
    }

    @Override public void exitAssign_expr(
            MicroParser.Assign_exprContext ctx) {

        String Lvalue = ptp.get(ctx).data.get("assign_Lvalue");
        String storeOp = "ERROR";
        String LvalueType = symbolTree.lookup(Lvalue).type;

        if (LvalueType.equals("INT")) {
            storeOp = "STOREI";
        } else if (LvalueType.equals("FLOAT")) {
            storeOp = "STOREF";
        }

        System.out.println(storeOp + " " + 
                ptp.get(ctx).data.get("register") + " " +
                Lvalue);
    }

    @Override public void exitId(
            MicroParser.IdContext ctx) {
        NodeProperties parentNodeProps = ptp.get(ctx.getParent());
        
        // if we're directly the child of an assign statement
        if (parentNodeProps.data.containsKey("assign_Lvalue") 
           // && parentNodeProps.data.get("assign_Lvalue") == null
                // ^^ might need this if we run into bugs later
            ) {
              addNodeProp(ctx.getParent(), "assign_Lvalue", ctx.getText());
        }
    }

    @Override public void exitAddop(
            MicroParser.AddopContext ctx) {
        addNodeProp(ctx, "addop", ctx.getText()); 
    }

    @Override public void exitPrimary(
            MicroParser.PrimaryContext ctx) {
        addNodeProp(ctx, "primary", ctx.getText());
    }

    @Override public void enterEveryRule(ParserRuleContext ctx){
        if (ctx.getText() != null) {
            ptp.put(ctx, new NodeProperties(ctx.getText()));
        }
    }

    @Override public void exitEveryRule(ParserRuleContext ctx){
        // put all entries from the current node's hash table 
        // into the parent node
        ParserRuleContext parent = ctx.getParent();
        if (parent != null) {
            NodeProperties parentNodeProps = ptp.get(ctx.getParent());
            parentNodeProps.data.putAll(ptp.get(ctx).data);
        }
    }

    @Override public void exitFactor(
            MicroParser.FactorContext ctx) {
        // check the expr_prefix which is an already-parsed 
        // child of the parent node
        NodeProperties expr_prefix = ptp.get(ctx.getParent().getChild(0));
        //System.out.println("exit factor expr_prefix: " + expr_prefix);

        if (!expr_prefix.toString().isEmpty()) {
          // generate add IR
          String type = symbolTree.lookup(
                  ptp.get(ctx).data.get("primary")).type;
          String temp = getNewRegister(type);
          String opcode = lookupOpcode(
                  expr_prefix.data.get("addop"), type);

          System.out.println(
                  opcode + " "
                 + expr_prefix.data.get("primary") + " "
                 + ptp.get(ctx).data.get("primary") + " "
                 + temp
                 );

          addNodeProp(ctx, "primary", temp);
        }
    }

    @Override public void exitExpr(MicroParser.ExprContext ctx) {
        //System.out.println("expr: " + ctx.getText());

        NodeProperties np = ptp.get(ctx);
        //System.out.println("exit expr: " + np.text);

        // pass up the last register if it exists
        if (ptp.get(ctx).data.containsKey("register")) {
          addNodeProp(ctx, "register", ptp.get(ctx).data.get("register"));
        } else {
          addNodeProp(ctx, "register", ptp.get(ctx).data.get("primary"));
        }
    }
    
    @Override public void exitExpr_prefix(
            MicroParser.Expr_prefixContext ctx) {
        //System.out.println("exit2 expr_prefix: " + ptp.get(ctx));
        NodeProperties parentProps = ptp.get(ctx.getParent());
    }

    @Override public void exitCompop(MicroParser.CompopContext ctx) {
        addNodeProp(ctx, "compop", ctx.getText());
    }

    @Override public void exitCond(MicroParser.CondContext ctx) {
        System.out.println( 
          lookupOpcode(ptp.get(ctx.getChild(1)).data.get("compop")) + " " + 
          ptp.get(ctx.getChild(0)).data.get("register") + " " + 
          ptp.get(ctx.getChild(2)).data.get("register") + " " +
          getNewLabel()
        );
    }
}
