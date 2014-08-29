import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) {
        ANTLRInputStream input = new ANTLRInputStream(args[0]);
        MicroLexer lexer = new MicroLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MicroParser parser = new MicroParser(tokens);

        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));
        
        
    }
}
