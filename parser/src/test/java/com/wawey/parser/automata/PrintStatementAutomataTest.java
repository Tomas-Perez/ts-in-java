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
public class PrintStatementAutomataTest {
    @Test
    public void shouldBuildTreeOf_PrintStatement_NumberLiteral_WhenGivenTokensForPrinting3() {
        ParserAutomata automata = new PrintStatementAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.PRINT, 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 6));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "3", 1, 7));
        automata.consume(TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 8));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
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
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_PrintStatement_AdditiveExp_WhenGivenTokensForPrinting1Plus1() {
        ParserAutomata automata = new PrintStatementAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.PRINT, 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 6));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 7));
        automata.consume(TokenImpl.forFixedToken(TokenType.PLUS, 1, 8));
        automata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 9));
        automata.consume(TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 10));
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
        ASTNode literal1 = buildLiteral.apply(new NumberLiteralNode(1, 7, "1"));
        ASTNode literal2 = buildLiteral.apply(new NumberLiteralNode(1, 9, "1"));
        ASTNode sum = new NonTerminalNode(
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

        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.PRINT_STATEMENT,
                sum
        );
        Assert.assertEquals(expected, automata.getResult());
    }
}
