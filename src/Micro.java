import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Micro {
    public static void main(String[] args) throws Exception {
        System.out.println(args[0]);
        ANTLRFileStream input = new ANTLRFileStream(args[0]);
        MicroLexer lexer = new MicroLexer(input);

        while(true) {
            Token token = lexer.nextToken();
            if (token.getType() == MicroLexer.EOF) {
                break;
            }
            String tokenName = MicroLexer.tokenNames[token.getType()];
            if (!tokenName.equals("WHITESPACE")) {
                System.out.println("Token Type: " + tokenName);
                System.out.println("Value " + token.getText());
            }
        }
               

        /*
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MicroParser parser = new MicroParser(tokens);

        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));
        */
        
        
    }
}
