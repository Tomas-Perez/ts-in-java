package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberLiteralNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
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
                        Collections.singletonList(
                                new NonTerminalNode(
                                        Rule.PRIMARY_EXPRESSION,
                                        Collections.singletonList(
                                                new NonTerminalNode(
                                                        Rule.LITERAL,
                                                        Collections.singletonList(new NumberLiteralNode(1, 1, "1"))
                                                )
                                        )
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
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1));
        Assert.assertTrue(automata.acceptable());
        ASTNode right = new NonTerminalNode(
                Rule.PRIMARY_EXPRESSION,
                Collections.singletonList(
                        new NonTerminalNode(
                                Rule.LITERAL,
                                Collections.singletonList(new NumberLiteralNode(1, 1, "1"))
                        )
                )
        );
        ASTNode left = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                Collections.singletonList(
                        new NonTerminalNode(
                                Rule.PRIMARY_EXPRESSION,
                                Collections.singletonList(
                                        new NonTerminalNode(
                                                Rule.LITERAL,
                                                Collections.singletonList(new NumberLiteralNode(1, 1, "1"))
                                        )
                                )
                        )
                )
        );

        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.MULTIPLICATIVE_EXPRESSION,
                        Collections.singletonList(
                                new NonTerminalNode(
                                        Rule.MULTIPLY_EXPRESSION,
                                        Arrays.asList(
                                                left,
                                                right
                                        )
                                )
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
                Collections.singletonList(
                        new NonTerminalNode(
                                Rule.LITERAL,
                                Collections.singletonList(n)
                        )
                )
        );

        ASTNode literal1 = literalFactory.apply(new NumberLiteralNode(1, 1, "1"));
        ASTNode literal2 = literalFactory.apply(new NumberLiteralNode(1, 3, "1"));
        ASTNode literal3 = literalFactory.apply(new NumberLiteralNode(1, 5, "1"));
        ASTNode mult1 = new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, Collections.singletonList(literal1));
        ASTNode mult2 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                Collections.singletonList(
                        new NonTerminalNode(
                                Rule.MULTIPLY_EXPRESSION,
                                Arrays.asList(
                                        mult1,
                                        literal2
                                )
                        )
                )
        );
        ASTNode mult3 = new NonTerminalNode(
                Rule.MULTIPLICATIVE_EXPRESSION,
                Collections.singletonList(
                        new NonTerminalNode(
                                Rule.MULTIPLY_EXPRESSION,
                                Arrays.asList(
                                        mult2,
                                        literal3
                                )
                        )
                )
        );

        Assert.assertEquals(
                mult3,
                automata.getResult()
        );
    }

}