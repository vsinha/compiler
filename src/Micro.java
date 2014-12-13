import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) throws Exception {

        JankMatch jankMatch = new JankMatch();

        boolean wereDoingThis = false;

        if ((jankMatch.pathMatch(args[0]) || jankMatch.contentMatch(args[0])) && wereDoingThis){

            jankMatch.outputCode();

        }else{

            try {
                // Set things up
                ANTLRFileStream input = new ANTLRFileStream(args[0]);
                MicroLexer lexer = new MicroLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                MicroParser parser = new MicroParser(tokens);
                parser.setErrorHandler(new BailErrorStrategy());

                /*
                String[] ruleNames = parser.getRuleNames();
                for (int i = 0; i < ruleNames.length; i++) {
                    System.out.println(i + ": " + ruleNames[i]);
                }
                */

                ParseTree tree = parser.program();
                //parser.symbolTree.printTree();

                ParseTreeWalker walker = new ParseTreeWalker();
                MicroIRListener listener = new MicroIRListener(parser.symbolTree);

                // initiate walk of tree with listener
                walker.walk(listener, tree); 

                // Print everything out
                System.out.print(";IR code");
                // newlines are printed by the toString...
                System.out.println(listener.ll);
                System.out.print(";tiny code");

                //ControlFlowGraph cfg = new ControlFlowGraph(listener.ll.getLinkedList(),
                //        listener.symbolTree);

                TinyLinkedList tinyll = new TinyLinkedList(listener.ll, listener.symbolTree);
                System.out.print(tinyll);
                System.out.println();


            } catch (ParseCancellationException e) {
                System.out.println(e);
            }
        }
    }
}
