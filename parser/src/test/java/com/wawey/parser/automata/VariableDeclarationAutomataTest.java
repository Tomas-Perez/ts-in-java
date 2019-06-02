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
public class VariableDeclarationAutomataTest {
    @Test
    public void shouldBuildTreeOf_VarDeclaration_Identifier_WhenGivenTokensForVarUndefinedDeclaration() {
        ParserAutomata automata = new VariableDeclarationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.VARIABLE_DECLARATION,
                new IdentifierNode(1, 2, "a")
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_VarDeclaration_IdentifierInitializer_WhenGivenTokensForVarDefinedDeclaration() {
        ParserAutomata automata = new VariableDeclarationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 3));
        automata.consume(new TokenImpl(TokenType.STRING_LITERAL, "\"a\"", 1, 4));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.VARIABLE_DECLARATION,
                new IdentifierNode(1, 2, "a"),
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
                                                        new StringLiteralNode(1, 4, "\"a\"")
                                                )
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_VarDeclaration_IdentifierTypeAnnotation_WhenGivenTokensForStringVarUndefinedDeclaration() {
        ParserAutomata automata = new VariableDeclarationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.STRING_TYPE, 1, 4));

        ASTNode expected = new NonTerminalNode(
                Rule.VARIABLE_DECLARATION,
                new IdentifierNode(1, 2, "a"),
                new NonTerminalNode(
                        Rule.TYPE_ANNOTATION,
                        new NonTerminalNode(
                                Rule.TYPE,
                                new StringTypeNode(1, 4)
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_VarDeclaration_IdentifierTypeAnnotationInitializer_WhenGivenTokensForStringVarDefinedDeclaration() {
        ParserAutomata automata = new VariableDeclarationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.STRING_TYPE, 1, 4));
        automata.consume(TokenImpl.forFixedToken(TokenType.EQUALS, 1, 10));
        automata.consume(new TokenImpl(TokenType.STRING_LITERAL, "\"a\"", 1, 11));

        ASTNode expected = new NonTerminalNode(
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
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_VarDeclaration_IdentifierTypeAnnotation_WhenGivenTokensForNumberVarUndefinedDeclaration() {
        ParserAutomata automata = new VariableDeclarationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.LET, 1, 1));
        automata.consume(new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2));
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 3));
        automata.consume(TokenImpl.forFixedToken(TokenType.NUMBER_TYPE, 1, 4));

        ASTNode expected = new NonTerminalNode(
                Rule.VARIABLE_DECLARATION,
                new IdentifierNode(1, 2, "a"),
                new NonTerminalNode(
                        Rule.TYPE_ANNOTATION,
                        new NonTerminalNode(
                                Rule.TYPE,
                                new NumberTypeNode(1, 4)
                        )
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }
}
