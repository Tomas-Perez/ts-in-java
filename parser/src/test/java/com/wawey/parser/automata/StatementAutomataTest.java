package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * @author Tomas Perez Molina
 */
public class StatementAutomataTest {
    @Test
    public void shouldBuildTreeOf_Statement_VarDeclaration_WhenGivenVarDeclarationTokens() {
        ParserAutomata automata = new StatementAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.STRING_TYPE, 1, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 10));
        automata.consume(new TokenImpl(TokenType.STRING_LITERAL, "\"a\"", 1, 11));

        ASTNode varDeclaration = new NonTerminalNode(
                Rule.VARIABLE_DECLARATION,
                new IdentifierNode(1, 2, "a"),
                new NonTerminalNode(
                        Rule.TYPE_ANNOTATION,
                        new NonTerminalNode(
                                Rule.TYPE,
                                new StringTypeNode(1, 4)
                        )
                ),
                new NonTerminalNode(
                        Rule.INITIALIZER,
                        new NonTerminalNode(
                                Rule.ADDITIVE_EXPRESSION,
                                new NonTerminalNode(
                                        Rule.MULTIPLICATIVE_EXPRESSION,
                                        new NonTerminalNode(
                                                Rule.PRIMARY_EXPRESSION,
                                                new NonTerminalNode(
                                                        Rule.LITERAL,
                                                        new StringLiteralNode(1, 11, "\"a\"")
                                                )
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.STATEMENT,
                        varDeclaration
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildTreeOf_Statement_PrintStatement_WhenGivenPrintStatementTokens() {
        ParserAutomata automata = new StatementAutomata();
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
        ASTNode print = new NonTerminalNode(
                Rule.PRINT_STATEMENT,
                sum
        );
        Assert.assertEquals(
                new NonTerminalNode(
                        Rule.STATEMENT,
                        print
                ),
                automata.getResult()
        );
    }

    @Test
    public void shouldBuildTreeOf_Statement_CoverInitializedName_WhenGivenTokensForVarReassignment() {
        ParserAutomata automata = new StatementAutomata();
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
                        Rule.STATEMENT,
                        coverInitializedName
                ),
                automata.getResult()
        );
    }
}
