package greeter;

import com.wawey.interpreter.Interpreter;
import com.wawey.interpreter.InterpreterImpl;
import com.wawey.interpreter.StandardOutPrinter;
import com.wawey.lexer.Lexer;
import com.wawey.lexer.LexerBuilder;
import com.wawey.lexer.Token;
import com.wawey.parser.AutomataParser;
import com.wawey.parser.Parser;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.ASTPrettyPrinter;
import com.wawey.parser.automata.FileAutomata;

import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public class Main {
    public static void main(String[] args) {
        String input = "let a: number;\n" +
                "a = 5 + 3 * 2;\n" +
                "print(a);";

        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        Interpreter interpreter = new InterpreterImpl(new StandardOutPrinter());

        List<Token> tokens = lexer.lex(input);
        ASTNode ast = parser.parse(tokens);

        interpreter.interpret(ast);
    }
}
