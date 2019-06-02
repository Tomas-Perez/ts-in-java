package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberLiteralNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class MultiplicativeExpressionAutomataTest {
    @Test
    public void shouldBuildATreeOf_MultiplicativeExp_PrimaryExp_WhenGivenALiteralToken() {
        ParserAutomata automata = new MultiplicativeExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        Assert.assertTrue(automata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.PRIMARY_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.LITERAL,
                                        new NumberLiteralNode(1, 1, "1")
                                )
                        )
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_MultiplicativeExp_MultiplyExp_WhenGivenTokensForMultiplication() {
        ParserAutomata automata = new MultiplicativeExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.ASTERISK, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        Assert.assertTrue(automata.acceptable());
        ASTNode left = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                new NumberLiteralNode(1, 1, "1")
                        )

                )

        );
        ASTNode right = new NonTerminalNode(
                Rule.PRIMARY_EXPRESSION,
                new NonTerminalNode(
                        Rule.LITERAL,
                        new NumberLiteralNode(1, 3, "1")
                )
        );


        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.MULTIPLY_EXPRESSION,
                                left,
                                right
                        )
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_MultiplicativeExp_DivideExp_WhenGivenTokensForDivision() {
        ParserAutomata automata = new MultiplicativeExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.FORWARD_SLASH, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        Assert.assertTrue(automata.acceptable());
        ASTNode left = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                new NumberLiteralNode(1, 1, "1")
                        )

                )

        );
        ASTNode right = new NonTerminalNode(
                Rule.PRIMARY_EXPRESSION,
                new NonTerminalNode(
                        Rule.LITERAL,
                        new NumberLiteralNode(1, 3, "1")
                )
        );


        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.DIVIDE_EXPRESSION,
                                left,
                                right
                        )
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_MultiplicativeExp_MultiplyExp_WhenGivenTokensForTriwayMultiplication() {
        ParserAutomata automata = new MultiplicativeExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.ASTERISK, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.ASTERISK, 1, 4));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 5));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> literalFactory = (n) -> new NonTerminalNode(
                Rule.PRIMARY_EXPRESSION,
                new NonTerminalNode(
                        Rule.LITERAL,
                        n
                )
        );

        ASTNode literal1 = literalFactory.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = literalFactory.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode literal3 = literalFactory.apply(new NumberLiteralNode(1, 5, "1"));
        ASTNode mult1 = new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, literal1);
        ASTNode mult2 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.MULTIPLY_EXPRESSION,
                        mult1,
                        literal2
                )
        );
        ASTNode mult3 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.MULTIPLY_EXPRESSION,
                        mult2,
                        literal3
                )
        );

        Assert.assertEquals(
                mult3,
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_MultiplicativeExp_DivideExp_WhenGivenTokensForTriwayDivision() {
        ParserAutomata automata = new MultiplicativeExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.FORWARD_SLASH, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.FORWARD_SLASH, 1, 4));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 5));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> literalFactory = (n) -> new NonTerminalNode(
                Rule.PRIMARY_EXPRESSION,
                new NonTerminalNode(
                        Rule.LITERAL,
                        n
                )
        );

        ASTNode literal1 = literalFactory.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = literalFactory.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode literal3 = literalFactory.apply(new NumberLiteralNode(1, 5, "1"));
        ASTNode mult1 = new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, literal1);
        ASTNode mult2 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.DIVIDE_EXPRESSION,
                        mult1,
                        literal2
                )
        );
        ASTNode mult3 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.DIVIDE_EXPRESSION,
                        mult2,
                        literal3
                )
        );

        Assert.assertEquals(
                mult3,
                automata.getResult()
        );
    }

}
