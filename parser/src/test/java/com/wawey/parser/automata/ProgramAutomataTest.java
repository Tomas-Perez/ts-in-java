package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.IdentifierNode;
import com.wawey.parser.ast.NonTerminalNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Perez Molina
 */
public class ProgramAutomataTest {

    @Test
    public void isNotAcceptableWhenGivenNothing() {
        ParserAutomata automata = new ProgramAutomata();
        Assert.assertFalse(automata.acceptable());
    }

    @Test
    public void shouldBuildATreeOf_Program_Line_WhenGivenASingleLine() {
        ParserAutomata automata = new ProgramAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 5));
        Assert.assertTrue(automata.acceptable());

        ASTNode expected = new NonTerminalNode(
                Rule.PROGRAM,
                new NonTerminalNode(
                        Rule.LINE,
                        new NonTerminalNode(
                                Rule.STATEMENT,
                                new NonTerminalNode(
                                        Rule.VARIABLE_DECLARATION,
                                        new IdentifierNode(1, 4, "a")
                                )
                        )
                )
        );

        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_Program_ProgramLine_ProgramLine_Line_WhenGiven3Lines() {
        ParserAutomata automata = new ProgramAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 5));
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 2, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "b", 2, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 2, 5));
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 3, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "c", 3, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 3, 5));
        Assert.assertTrue(automata.acceptable());

        ASTNode expected = new NonTerminalNode(
                Rule.PROGRAM,
                new NonTerminalNode(
                        Rule.PROGRAM,
                        new NonTerminalNode(
                                Rule.PROGRAM,
                                new NonTerminalNode(
                                        Rule.LINE,
                                        new NonTerminalNode(
                                                Rule.STATEMENT,
                                                new NonTerminalNode(
                                                        Rule.VARIABLE_DECLARATION,
                                                        new IdentifierNode(1, 4, "a")
                                                )
                                        )
                                )
                        ),
                        new NonTerminalNode(
                                Rule.LINE,
                                new NonTerminalNode(
                                        Rule.STATEMENT,
                                        new NonTerminalNode(
                                                Rule.VARIABLE_DECLARATION,
                                                new IdentifierNode(2, 4, "b")
                                        )
                                )
                        )
                ),
                new NonTerminalNode(
                        Rule.LINE,
                        new NonTerminalNode(
                                Rule.STATEMENT,
                                new NonTerminalNode(
                                        Rule.VARIABLE_DECLARATION,
                                        new IdentifierNode(3, 4, "c")
                                )
                        )
                )
        );

        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_Program_ProgramLine_Line_WhenGiven2Lines() {
        ParserAutomata automata = new ProgramAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 5));
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 2, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "b", 2, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 2, 5));
        Assert.assertTrue(automata.acceptable());

        ASTNode expected = new NonTerminalNode(
                Rule.PROGRAM,
                new NonTerminalNode(
                        Rule.PROGRAM,
                        new NonTerminalNode(
                                Rule.LINE,
                                new NonTerminalNode(
                                        Rule.STATEMENT,
                                        new NonTerminalNode(
                                                Rule.VARIABLE_DECLARATION,
                                                new IdentifierNode(1, 4, "a")
                                        )
                                )
                        )
                ),
                new NonTerminalNode(
                        Rule.LINE,
                        new NonTerminalNode(
                                Rule.STATEMENT,
                                new NonTerminalNode(
                                        Rule.VARIABLE_DECLARATION,
                                        new IdentifierNode(2, 4, "b")
                                )
                        )
                )
        );

        Assert.assertEquals(expected, automata.getResult());
    }
}
