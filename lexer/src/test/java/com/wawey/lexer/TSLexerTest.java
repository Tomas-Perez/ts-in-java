package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TSLexerTest {
    @Test
    public void lexesNumberPrintStatement() {
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex("print(3);");
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 6),
                new TokenImpl(TokenType.NUMBER_LITERAL, "3", 1, 7),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 8),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 9),
                new TokenImpl(TokenType.EOF, "", 1, 10)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesStringPrintStatement() {
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex("print(\"hello, world!\");");
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 6),
                new TokenImpl(TokenType.STRING_LITERAL, "\"hello, world!\"", 1, 7),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 22),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 23),
                new TokenImpl(TokenType.EOF, "", 1, 24)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesDeclarationAsignationAndPrint() {
        String input =
                "let a: number;\n" +
                        "a = 3;\n" +
                        "print(a);";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                new TokenImpl(TokenType.IDENTIFIER, "a", 1, 5),
                TokenImpl.forFixedToken(TokenType.COLON, 1, 6),
                TokenImpl.forFixedToken(TokenType.NUMBER_TYPE, 1, 8),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 14),
                new TokenImpl(TokenType.IDENTIFIER, "a", 2, 1),
                TokenImpl.forFixedToken(TokenType.EQUALS, 2, 3),
                new TokenImpl(TokenType.NUMBER_LITERAL, "3", 2, 5),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 2, 6),
                TokenImpl.forFixedToken(TokenType.PRINT, 3, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 3, 6),
                new TokenImpl(TokenType.IDENTIFIER, "a", 3, 7),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 3, 8),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 3, 9),
                new TokenImpl(TokenType.EOF, "", 3, 10)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesSumPrint() {
        String input = "print(1+2);";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.PRINT, 1, 1),
                TokenImpl.forFixedToken(TokenType.LEFT_PAREN, 1, 6),
                new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 7),
                TokenImpl.forFixedToken(TokenType.PLUS, 1, 8),
                new TokenImpl(TokenType.NUMBER_LITERAL, "2", 1, 9),
                TokenImpl.forFixedToken(TokenType.RIGHT_PAREN, 1, 10),
                TokenImpl.forFixedToken(TokenType.SEMICOLON, 1, 11),
                new TokenImpl(TokenType.EOF, "", 1, 12)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesPI() {
        String input = "3.14";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.NUMBER_LITERAL, "3.14", 1, 1),
                new TokenImpl(TokenType.EOF, "", 1, 5)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesANumberBelow1() {
        String input = "0.14";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.NUMBER_LITERAL, "0.14", 1, 1),
                new TokenImpl(TokenType.EOF, "", 1, 5)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesAReallySmallNumber() {
        String input = "0.04";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.NUMBER_LITERAL, "0.04", 1, 1),
                new TokenImpl(TokenType.EOF, "", 1, 5)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesJust1() {
        String input = "1";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.NUMBER_LITERAL, "1", 1, 1),
                new TokenImpl(TokenType.EOF, "", 1, 2)
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lexesJust0() {
        String input = "0";
        Lexer tsLexer = LexerFactory.getTSLexer();
        List<Token> actual = tsLexer.lex(input);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.NUMBER_LITERAL, "0", 1, 1),
                new TokenImpl(TokenType.EOF, "", 1, 2)
        );
        Assert.assertEquals(expected, actual);
    }
}
