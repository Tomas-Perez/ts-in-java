package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.IdentifierNode;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberLiteralNode;
import org.junit.Assert;
import org.junit.Test;

public class PrimaryExpressionAutomataTest {
    @Test
    public void shouldBuildATreeOf_PrimaryExp_Literal_NumberLiteral_WhenGivenNumberLiteralToken() {
        ParserAutomata parserAutomata = new PrimaryExpressionAutomata();
        parserAutomata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "12", 1, 1));
        Assert.assertTrue(parserAutomata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                new NumberLiteralNode(1, 1, "12")
                        )
                ),
                parserAutomata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_PrimaryExp_Identifier_WhenGivenIdentifierToken() {
        ParserAutomata parserAutomata = new PrimaryExpressionAutomata();
        parserAutomata.consume(new TokenImpl(TokenType.IDENTIFIER, "abc12", 1, 1));
        Assert.assertTrue(parserAutomata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new IdentifierNode(1, 1, "abc12")
                ),
                parserAutomata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_PrimaryExp_Identifier_WhenGivenIdentifierTokenBetweenParentesis() {
        ParserAutomata parserAutomata = new PrimaryExpressionAutomata();
        parserAutomata.consume(TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 1));
        parserAutomata.consume(new TokenImpl(TokenType.IDENTIFIER, "abc12", 1, 2));
        parserAutomata.consume(TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 7));
        Assert.assertTrue(parserAutomata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new IdentifierNode(1, 2, "abc12")
                ),
                parserAutomata.getResult()
        );
    }

}
