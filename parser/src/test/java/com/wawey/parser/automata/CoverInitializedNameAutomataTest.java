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
public class CoverInitializedNameAutomataTest {
    @Test
    public void shouldMakeTreeOf_CoverName_IdentifierInitializer_Literal_WhenGivenVariableReassignmentTokens() {
        ParserAutomata automata = new CoverInitializedNameAutomata();
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        Assert.assertTrue(automata.acceptable());
        ASTNode literal = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.PRIMARY_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.LITERAL,
                                        new NumberLiteralNode(1, 3, "1")
                                )
                        )
                )
        );
        ASTNode expected = new NonTerminalNode(
                Rule.COVER_INITIALIZED_NAME,
                new IdentifierNode(1, 1, "a"),
                new NonTerminalNode(
                        Rule.INITIALIZER,
                        literal
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldMakeTreeOf_CoverName_IdentifierInitializer_Identifier_WhenGivenVariableReassignmentTokens() {
        ParserAutomata automata = new CoverInitializedNameAutomata();
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 2));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 3));
        Assert.assertTrue(automata.acceptable());
        ASTNode identifier = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.PRIMARY_EXPRESSION,
                                new IdentifierNode(1, 3, "a")
                        )
                )
        );
        ASTNode expected = new NonTerminalNode(
                Rule.COVER_INITIALIZED_NAME,
                new IdentifierNode(1, 1, "a"),
                new NonTerminalNode(
                        Rule.INITIALIZER,
                        identifier
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }
}
