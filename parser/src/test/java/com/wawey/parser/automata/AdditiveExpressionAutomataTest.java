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

/**
 * @author Tomas Perez Molina
 */
public class AdditiveExpressionAutomataTest {
    @Test
    public void shouldBuildATreeOf_AdditiveExp_AddExp_WhenGivenTokensForSum() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.PLUS, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> buildLiteral = (n) -> new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                n
                        )
                )
        );
        ASTNode literal1 = buildLiteral.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = buildLiteral.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode expected = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUM_EXPRESSION,
                        new NonTerminalNode(
                                Rule.ADDITIVE_EXPRESSION,
                                literal1
                        ),
                        literal2
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_AdditiveExp_AddExp_WhenGivenTokensForTriwaySum() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.PLUS, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.PLUS, 1, 4));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 5));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> buildLiteral = (n) -> new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                n
                        )
                )
        );
        ASTNode literal1 = buildLiteral.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = buildLiteral.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode literal3 = buildLiteral.apply(new NumberLiteralNode(1, 5, "1"));
        ASTNode add1 = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUM_EXPRESSION,
                        new NonTerminalNode(
                                Rule.ADDITIVE_EXPRESSION,
                                literal1
                        ),
                        literal2
                )
        );
        ASTNode expected = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUM_EXPRESSION,
                        add1,
                        literal3
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_AdditiveExp_SubtractExp_WhenGivenTokensForSubtraction() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.MINUS, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> buildLiteral = (n) -> new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                n
                        )
                )
        );
        ASTNode literal1 = buildLiteral.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = buildLiteral.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode expected = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUBTRACT_EXPRESSION,
                        new NonTerminalNode(
                                Rule.ADDITIVE_EXPRESSION,
                                literal1
                        ),
                        literal2
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_AdditiveExp_SubtractExp_WhenGivenTokensForTriwaySubtraction() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.MINUS, 1, 2));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.MINUS, 1, 4));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 5));
        Assert.assertTrue(automata.acceptable());
        Function<NumberLiteralNode, ASTNode> buildLiteral = (n) -> new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.PRIMARY_EXPRESSION,
                        new NonTerminalNode(
                                Rule.LITERAL,
                                n
                        )
                )
        );
        ASTNode literal1 = buildLiteral.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = buildLiteral.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode literal3 = buildLiteral.apply(new NumberLiteralNode(1, 5, "1"));
        ASTNode add1 = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUBTRACT_EXPRESSION,
                        new NonTerminalNode(
                                Rule.ADDITIVE_EXPRESSION,
                                literal1
                        ),
                        literal2
                )
        );
        ASTNode expected = new NonTerminalNode(
                Rule.ADDITIVE_EXPRESSION,
                new NonTerminalNode(
                        Rule.SUBTRACT_EXPRESSION,
                        add1,
                        literal3
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildATreeOf_AdditiveExp_MultiplicativeExp_MultiplyExp_WhenGivenTokensForMultiplication() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
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
                        Rule.ADDITIVE_EXPRESSION,
                        new NonTerminalNode(
                                Rule.MULTIPLICATIVE_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.MULTIPLY_EXPRESSION,
                                        left,
                                        right
                                )
                        )
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_AdditiveExp_MultiplicativeExp_MultiplyExp_WhenGivenTokensForTriwayMultiplication() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
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
                new NonTerminalNode(
                        Rule.ADDITIVE_EXPRESSION,
                        mult3
                ),
                automata.getResult()
        );
    }

    @Test
    public void mix() {
        ParserAutomata automata = new AdditiveExpressionAutomata();
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "5", 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.PLUS, 1, 3));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "3", 1, 5));
        automata.consume(TokenImpl.forFixedToken(TokenType.ASTERISK, 1, 7));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "2", 1, 9));
        Assert.assertTrue(automata.acceptable());
    }
}
