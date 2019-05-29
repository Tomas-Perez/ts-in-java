package greeter;

/**
 * @author Tomas Perez Molina
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
        new InterpreterGreeter().greet();
        new LexerGreeter().greet();
        new ParserGreeter().greet();
    }
}
