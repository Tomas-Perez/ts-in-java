package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TSLexerTest {
    @Test
    public void lexesNumberPrintStatement() {
        Lexer tsLexer = LexerBuilder.buildTSLexer();
        List<Token> actual = tsLexer.lex("print(3);");
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 6, 1),
                new TokenImpl(TokenType.NUMBER_LITERAL, "3", 7, 1),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 8, 1),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 9, 1)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesStringPrintStatement() {
        Lexer tsLexer = LexerBuilder.buildTSLexer();
        List<Token> actual = tsLexer.lex("print(\"hello, world!\");");
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 6, 1),
                new TokenImpl(TokenType.STRING_LITERAL, "\"hello, world!\"", 7, 1),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 22, 1),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 23, 1)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesDeclarationAsignationAndPrint() {
        String input =
                "let a: number;\n" +
                        "a = 3;\n" +
                        "print(a);";
        Lexer tsLexer = LexerBuilder.buildTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                new TokenImpl(TokenType.IDENTIFIER, "a", 5, 1),
                TokenImpl.forFixedToken(TokenType.COLON, 6, 1),
                TokenImpl.forFixedToken(TokenType.NUMBER_TYPE, 8, 1),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 14, 1),
                new TokenImpl(TokenType.IDENTIFIER, "a", 1, 2),
                TokenImpl.forFixedToken(TokenType.EQUALS, 3, 2),
                new TokenImpl(TokenType.NUMBER_LITERAL, "3", 5, 2),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 6, 2),
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 3),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 6, 3),
                new TokenImpl(TokenType.IDENTIFIER, "a", 7, 3),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 8, 3),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 9, 3)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesSumPrint() {
        String input = "print(1+2);";
        Lexer tsLexer = LexerBuilder.buildTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 6, 1),
                new TokenImpl(TokenType.NUMBER_LITERAL, "1", 7, 1),
                TokenImpl.forFixedToken(TokenType.PLUS, 8, 1),
                new TokenImpl(TokenType.NUMBER_LITERAL, "2", 9, 1),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 10, 1),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 11, 1)
        );
        Assert.assertEquals(expected, actual);
    }
}