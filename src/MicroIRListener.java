import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener {

    @Override
    public void visitTerminal(TerminalNode node) {
        System.out.println(node);
    }

    @Override
    public void enterAssign_stmt(
            MicroParser.Assign_stmtContext ctx) {
        
        System.out.println(ctx);
    }
}
