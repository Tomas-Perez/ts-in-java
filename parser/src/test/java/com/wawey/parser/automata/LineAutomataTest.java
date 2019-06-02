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
public class LineAutomataTest {
    @Test
    public void shouldBuildTreeOf_Line_Statement_WhenGivenStatementTokensAndSemicolon() {
        ParserAutomata automata = new LineAutomata();
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 2));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 3));
        Assert.assertFalse(automata.acceptable());
        automata.consume(TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 4));
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
        ASTNode coverInitializedName = new NonTerminalNode(
                Rule.COVER_INITIALIZED_NAME,
                new IdentifierNode(1, 1, "a"),
                new NonTerminalNode(
                        Rule.INITIALIZER,
                        identifier
                )
        );
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.LINE,
                        new NonTerminalNode(
                                Rule.STATEMENT,
                                coverInitializedName
                        )
                ),
                automata.getResult()
        );
    }
}
