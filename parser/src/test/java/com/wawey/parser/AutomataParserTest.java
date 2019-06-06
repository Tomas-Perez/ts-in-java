package com.wawey.parser;

import com.wawey.lexer.Lexer;
import com.wawey.lexer.LexerBuilder;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.*;
import com.wawey.parser.automata.FileAutomata;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AutomataParserTest {
    @Test
    public void shouldBuildTreeOf_File_PrintStatement_WhenParsingPrint3() {
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
    public void shouldBuildTreeOf_File_PrintStatement_WhenParsingPrint3Plus3() {
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        ASTNode result = parser.parse(lexer.lex("print(3+3);"));
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
                                                                Rule.SUM_EXPRESSION,
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
                                                                ),
                                                                new NonTerminalNode(
                                                                        Rule.MULTIPLICATIVE_EXPRESSION,
                                                                        new NonTerminalNode(
                                                                                Rule.PRIMARY_EXPRESSION,
                                                                                new NonTerminalNode(
                                                                                        Rule.LITERAL,
                                                                                        new NumberLiteralNode(1, 9, "3")
                                                                                )
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
    public void shouldBuildTreeOf_VariableDeclaration() {
        String input = "let a : number;";
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        List<Token> tokens = lexer.lex(input);
        ASTNode result = parser.parse(tokens);
        ASTNode expected = new NonTerminalNode(
                Rule.FILE,
                new NonTerminalNode(
                        Rule.PROGRAM,
                        new NonTerminalNode(
                                Rule.LINE,
                                new NonTerminalNode(
                                        Rule.STATEMENT,
                                        new NonTerminalNode(
                                                Rule.VARIABLE_DECLARATION,
                                                new IdentifierNode(1, 5, "a"),
                                                new NonTerminalNode(
                                                        Rule.TYPE_ANNOTATION,
                                                        new NonTerminalNode(
                                                                Rule.TYPE,
                                                                new NumberTypeNode(1, 9)
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
    public void shouldBuildTreeOf_MultipleVariableDeclarations() {
        String input = "let a : number;let a : number;";
        Lexer lexer = LexerBuilder.buildTSLexer();
        Parser parser = new AutomataParser(new FileAutomata());
        List<Token> tokens = lexer.lex(input);
        tokens.stream().map(t -> String.format("%s, %s", t.getType(), t.getLexeme())).forEach(System.out::println);
        ASTNode result = parser.parse(tokens);
        ASTNode firstVar = new NonTerminalNode(
                Rule.LINE,
                new NonTerminalNode(
                        Rule.STATEMENT,
                        new NonTerminalNode(
                                Rule.VARIABLE_DECLARATION,
                                new IdentifierNode(1, 5, "a"),
                                new NonTerminalNode(
                                        Rule.TYPE_ANNOTATION,
                                        new NonTerminalNode(
                                                Rule.TYPE,
                                                new NumberTypeNode(1, 9)
                                        )
                                )
                        )
                )
        );
        ASTNode secondVar = new NonTerminalNode(
                Rule.LINE,
                new NonTerminalNode(
                        Rule.STATEMENT,
                        new NonTerminalNode(
                                Rule.VARIABLE_DECLARATION,
                                new IdentifierNode(1, 20, "a"),
                                new NonTerminalNode(
                                        Rule.TYPE_ANNOTATION,
                                        new NonTerminalNode(
                                                Rule.TYPE,
                                                new NumberTypeNode(1, 24)
                                        )
                                )
                        )
                )
        );

        ASTNode expected = new NonTerminalNode(
                Rule.FILE,
                new NonTerminalNode(
                        Rule.PROGRAM,
                        new NonTerminalNode(
                                Rule.PROGRAM,
                                firstVar
                        ),
                        secondVar
                )
        );
        Assert.assertEquals(expected, result);
    }
}