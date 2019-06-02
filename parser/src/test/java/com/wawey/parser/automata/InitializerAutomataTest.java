package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Perez Molina
 */
public class InitializerAutomataTest {
    @Test
    public void shouldBuildATreeOf_Initializer_Identifier_WhenGivenTokensForInitializerWithVariable() {
        ParserAutomata automata = new InitializerAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.INITIALIZER,
                new NonTerminalNode(
                        Rule.ADDITIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.MULTIPLICATIVE_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.PRIMARY_EXPRESSION,
                                        new IdentifierNode(1, 2, "a")
                                )
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_Initializer_StringLit_WhenGivenTokensForInitializerWithString() {
        ParserAutomata automata = new InitializerAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 1));
        automata.consume(new TokenImpl(TokenType.STRING_LITERAL, "\"a\"", 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.INITIALIZER,
                new NonTerminalNode(
                        Rule.ADDITIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.MULTIPLICATIVE_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.PRIMARY_EXPRESSION,
                                        new NonTerminalNode(
                                                Rule.LITERAL,
                                                new StringLiteralNode(1, 2, "\"a\"")
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_Initializer_NumberLit_WhenGivenTokensForInitializerWithNumber() {
        ParserAutomata automata = new InitializerAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 1));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.INITIALIZER,
                new NonTerminalNode(
                        Rule.ADDITIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.MULTIPLICATIVE_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.PRIMARY_EXPRESSION,
                                        new NonTerminalNode(
                                                Rule.LITERAL,
                                                new NumberLiteralNode(1, 2, "1")
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }
}
