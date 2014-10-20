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
            MicroIRListener listener = new MicroIRListener(parser.symbolTree);


            // initiate walk of tree with listener
            walker.walk(listener, tree); 

            // Print everything out
            System.out.print("\n;IR code");
            // newlines are printed by the toString...
            System.out.println(listener.ll);
            System.out.print(";tiny code");

            TinyLinkedList tinyll = new TinyLinkedList(listener.ll);
            System.out.print(tinyll);


        } catch (ParseCancellationException e) {
            System.out.println(e);
        }
    }
}
