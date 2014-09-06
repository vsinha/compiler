import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) throws Exception {
        ANTLRFileStream input = new ANTLRFileStream(args[0]);
        MicroLexer lexer = new MicroLexer(input);

        /*
        // For stage1
        while(true) {
            Token token = lexer.nextToken();
            if (token.getType() == MicroLexer.EOF) {
                break;
            }
            System.out.println("Token Type: " 
                    + MicroLexer.tokenNames[token.getType()]);
            System.out.println("Value: " + token.getText());
        }
        */
               
        // Set things up
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MicroParser parser = new MicroParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());

        // This line actually parses the input
        try {
            ParseTree tree = parser.program();
        } catch (ParseCancellationException e) {
            System.out.println("Not Accepted");
        }
    }
}
