import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) throws Exception {
        ANTLRFileStream input = new ANTLRFileStream(args[0]);
        MicroLexer lexer = new MicroLexer(input);

        // Set things up
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MicroParser parser = new MicroParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());

        // This line actually parses the input
        Boolean success = true;
        try {
            ParseTree tree = parser.program();
        } catch (ParseCancellationException e) {
            success = false;
            System.out.print("Not accepted");
        }

        if (success) {
            System.out.print("Accepted");
        }
    }
}
