package com.wawey.tsinjava;

import com.wawey.interpreter.Interpreter;
import com.wawey.interpreter.InterpreterFactory;
import com.wawey.interpreter.StandardOutPrinter;
import com.wawey.lexer.Lexer;
import com.wawey.lexer.LexerFactory;
import com.wawey.lexer.Token;
import com.wawey.parser.Parser;
import com.wawey.parser.ParserFactory;
import com.wawey.parser.ast.ASTNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please supply a file path as a single argument");
        }
        String input = new String(Files.readAllBytes(Paths.get(args[0])));
        System.out.println("--------------- INPUT ---------------\n\n");
        System.out.println(input);
        System.out.println("\n\n");
        System.out.println("--------------- OUTPUT ---------------\n\n");


        LexerFactory lexerFactory = new LexerFactory();

        Lexer lexer = lexerFactory.getTSLexer();
        Parser parser = ParserFactory.getTSParser();
        Interpreter interpreter = InterpreterFactory.getTSInterpreter(new StandardOutPrinter());

        try {
            List<Token> tokens = lexer.lex(input);
            ASTNode ast = parser.parse(tokens);
            interpreter.interpret(ast);
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
}
