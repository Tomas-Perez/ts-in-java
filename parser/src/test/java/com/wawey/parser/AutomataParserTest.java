package com.wawey.parser;

import com.wawey.lexer.Lexer;
import com.wawey.lexer.LexerBuilder;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.ASTPrettyPrinter;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberLiteralNode;
import com.wawey.parser.automata.FileAutomata;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AutomataParserTest {
    @Test
    public void shouldBuildTreeOf_File_PrintStatment_WhenParsingPrint3() {
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        ASTNode result = parser.parse(lexer.lex("print(3);"));
        ASTNode expected = new NonTerminalNode(
                Rule.FILE,
                new NonTerminalNode(
                        Rule.PROGRAM,
                        new NonTerminalNode(
                                Rule.LINE,
                                new NonTerminalNode(
                                        Rule.STATEMENT,
                                        new NonTerminalNode(
                                                Rule.PRINT_STATEMENT,
                                                new NonTerminalNode(
                                                        Rule.ADDITIVE_EXPRESSION,
                                                        new NonTerminalNode(
                                                                Rule.MULTIPLICATIVE_EXPRESSION,
                                                                new NonTerminalNode(
                                                                        Rule.PRIMARY_EXPRESSION,
                                                                        new NonTerminalNode(
                                                                                Rule.LITERAL,
                                                                                new NumberLiteralNode(1, 7, "3")
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldBuildTreeOf_File_PrintStatment_WhenParsingPrint3Plus3() {
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        ASTNode result = parser.parse(lexer.lex("print(3+3);"));
        ASTPrettyPrinter.prettyPrint(result);
    }

    @Test
    public void shouldBuildTreeOf_MultipleLines() {
        String input = "let a : number;";
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        List<Token> tokens = lexer.lex(input);
        tokens.stream().map(t -> String.format("%s, %s", t.getType(), t.getLexeme())).forEach(System.out::println);
        ASTNode result = parser.parse(tokens);
        ASTPrettyPrinter.prettyPrint(result);
    }

    @Test
    public void shouldBuildTreeOf_MultipleLinesdasd() {
        String input = "let a : number;let a : number;";
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        List<Token> tokens = lexer.lex(input);
        tokens.stream().map(t -> String.format("%s, %s", t.getType(), t.getLexeme())).forEach(System.out::println);
        ASTNode result = parser.parse(tokens);
        ASTPrettyPrinter.prettyPrint(result);
    }
}