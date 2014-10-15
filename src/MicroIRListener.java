import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener {
    SymbolTableTree tree;

    // This store information about nodes we've visited.
    ParseTreeProperty<NodeProperties> ptp;
    
    public MicroIRListener(SymbolTableTree tree) {
        this.tree = tree;
        this.ptp = new ParseTreeProperty<NodeProperties>();
    }

    @Override public void visitTerminal(TerminalNode node) {
        //System.out.println(node);
    }

    @Override public void enterAssign_stmt(
            MicroParser.Assign_stmtContext ctx) {
        //System.out.println(ctx);
    }

    @Override public void enterAssign_expr(
            MicroParser.Assign_exprContext ctx) {
        //System.out.println(ctx.toStringTree());
    }

    @Override public void exitId(
            MicroParser.IdContext ctx) {
        //System.out.println(ctx.toStringTree());
    }

    @Override public void enterAddop(
            MicroParser.AddopContext ctx) {
        //System.out.println("addop: " + ctx.getText());
    }

    @Override public void exitAddop(
            MicroParser.AddopContext ctx) {
        System.out.println("addop: " + ctx.getText());
        NodeProperties parentNodeProps = ptp.get(ctx.getParent());

        parentNodeProps.operator = ctx.getText();
    }

    @Override public void enterPrimary(
            MicroParser.PrimaryContext ctx) {
        System.out.println("primary: " + ctx.getText());
    }

    @Override public void exitPrimary(
            MicroParser.PrimaryContext ctx) {
        System.out.println("primary: " + ctx.getText());
        setParentLREntries(ctx, ctx.getText());
    }

    public void passToParent(ParserRuleContext ctx, String str){
        ParserRuleContext parent = ctx.getParent();
        if (parent != null) {
          NodeProperties parentNodeProps = ptp.get(ctx.getParent());
          if (str != "null") {
            parentNodeProps.text = parentNodeProps.text + " " + str;
          }
        }
    }

    @Override public void enterPostfix_expr(
            MicroParser.Postfix_exprContext ctx) {
        System.out.println("postfix: " + ctx.getText());
        ptp.put(ctx, new NodeProperties());
    }

    @Override public void exitPostfix_expr(
            MicroParser.Postfix_exprContext ctx) {
        System.out.println("postfix: " + ctx.getText());
        System.out.println("postfix2: " + ptp.get(ctx).Ltext 
                    + " " + ptp.get(ctx).Rtext);
        ptp.get(ctx.getParent()).Ltext = ptp.get(ctx).Ltext;
        ptp.get(ctx.getParent()).Rtext = ptp.get(ctx).Rtext;
    }

    public void setParentLREntries(ParserRuleContext ctx, 
            String LRtext) {
        NodeProperties parentNodeProps = ptp.get(ctx.getParent());
        if (parentNodeProps.Ltext == null) {
            parentNodeProps.Ltext = LRtext;
        } else {
            parentNodeProps.Rtext = LRtext;
        }
    }

    @Override public void enterFactor_prefix(
            MicroParser.Factor_prefixContext ctx) {
        System.out.println("factor: " + ctx.getText());
        System.out.println("factor2: " + ptp.get(ctx));
    }

    @Override public void exitFactor_prefix(
            MicroParser.Factor_prefixContext ctx) {
        System.out.println("factor3: " + ctx.getText());
        System.out.println("factor4: " + ptp.get(ctx));
    }

    @Override public void enterEveryRule(ParserRuleContext ctx){
        ptp.put(ctx, new NodeProperties());
    }
    
    @Override public void exitEveryRule(ParserRuleContext ctx){
        ptp.put(ctx, new NodeProperties());
        passToParent(ctx, ctx.getText());
    }

    @Override public void enterFactor(
            MicroParser.FactorContext ctx) {
        System.out.println("factor: " + ctx.getText());
    }

    @Override public void enterExpr(MicroParser.ExprContext ctx) {
        System.out.println("expr: " + ctx.getText());
    }

    @Override public void exitExpr(MicroParser.ExprContext ctx) {
        System.out.println("expr: " + ctx.getText());

        NodeProperties np = ptp.get(ctx);
        System.out.println("exit expr: " + np.text);
    }
    
    @Override public void enterExpr_prefix(
            MicroParser.Expr_prefixContext ctx) {
        System.out.println("expr_prefix: " + ctx.getText());
    }

    @Override public void exitExpr_prefix(
            MicroParser.Expr_prefixContext ctx) {
        System.out.println("expr_prefix: " + ctx.getText());
        System.out.println("exit expr_prefix " + ptp.get(ctx));
        //ptp.get(ctx.getParent()).leftNode = ptp.get(ctx).toString();
    }
}
