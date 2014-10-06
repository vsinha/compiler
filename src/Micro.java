import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) throws Exception {
        try {
            // Set things up
            ANTLRFileStream input = new ANTLRFileStream(args[0]);
            MicroLexer lexer = new MicroLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            MicroParser parser = new MicroParser(tokens);
            parser.setErrorHandler(new BailErrorStrategy());

            ParseTree tree = parser.program();
            //parser.symbolTree.printTree();

            ParseTreeWalker walker = new ParseTreeWalker();
            MicroListener listener = new MicroListener();

            // initiate walk of tree with listener
            walker.walk(listener, tree); 

        } catch (ParseCancellationException e) {
            System.out.println(e);
        }
    }
}
