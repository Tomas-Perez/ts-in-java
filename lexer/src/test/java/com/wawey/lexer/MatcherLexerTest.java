package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatcherLexerTest {
    @Test
    public void shouldDetectSingleLetToken() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        Lexer letLexer = new MatcherLexer(matcher);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                TokenImpl.forFixedToken(TokenType.EOF, 1, 4)
        );
        Assert.assertEquals(expected, letLexer.lex("let"));
    }

    @Test
    public void shouldDetectMultipleLetTokensInOneLine() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        Lexer letLexer = new MatcherLexer(matcher);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                TokenImpl.forFixedToken(TokenType.LET, 1, 4),
                TokenImpl.forFixedToken(TokenType.LET, 1, 7),
                TokenImpl.forFixedToken(TokenType.LET, 1, 10),
                TokenImpl.forFixedToken(TokenType.LET, 1, 13),
                TokenImpl.forFixedToken(TokenType.EOF, 1, 16)
        );
        Assert.assertEquals(expected, letLexer.lex("letletletletlet"));
    }

    @Test
    public void shouldDetectMultipleLetTokensInMultipleLines() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        Automata newLineAutomata = new AutomataFactory().singleCharAutomata('\n');
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher newLineMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, newLineAutomata);
        Lexer letLexer = new MatcherLexer(letMatcher, newLineMatcher);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                TokenImpl.forFixedToken(TokenType.LET, 1, 4),
                TokenImpl.forFixedToken(TokenType.LET, 2, 1),
                TokenImpl.forFixedToken(TokenType.LET, 2, 4),
                TokenImpl.forFixedToken(TokenType.LET, 2, 7),
                TokenImpl.forFixedToken(TokenType.EOF, 2, 10)
        );
        Assert.assertEquals(expected, letLexer.lex("letlet\nletletlet"));
    }

    @Test
    public void shouldDetectATokenBetweenMultipleNewLines() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        Automata newLineAutomata = new AutomataFactory().singleCharAutomata('\n');
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher newLineMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, newLineAutomata);
        Lexer letLexer = new MatcherLexer(letMatcher, newLineMatcher);
        List<Token> expected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 2, 1),
                TokenImpl.forFixedToken(TokenType.EOF, 3, 1)
        );
        Assert.assertEquals(expected, letLexer.lex("\nlet\n"));
    }

    @Test
    public void shouldPrioritizeTokensByMatcherOrder() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher otherMatcher = new AutomataTokenMatcher(TokenType.IDENTIFIER, letAutomata);
        Lexer letFirstLexer = new MatcherLexer(letMatcher, otherMatcher);
        List<Token> letExpected = Arrays.asList(
                TokenImpl.forFixedToken(TokenType.LET, 1, 1),
                TokenImpl.forFixedToken(TokenType.EOF, 1, 4)
        );
        Assert.assertEquals(letExpected, letFirstLexer.lex("let"));

        Lexer idFirstLexer = new MatcherLexer(otherMatcher, letMatcher);
        List<Token> idExpected = Arrays.asList(
                new TokenImpl(TokenType.IDENTIFIER, "let", 1, 1),
                TokenImpl.forFixedToken(TokenType.EOF, 1, 4)
        );
        Assert.assertEquals(idExpected, idFirstLexer.lex("let"));
    }

    @Test(expected = LexicalError.class)
    public void baseLexerShouldThrowLexicalErrorWhenGivenAnything() {
        Lexer baseLexer = new MatcherLexer();
        baseLexer.lex("a");
    }

    @Test
    public void baseLexerShouldReturnEOFWhenGivenEmptyString() {
        Lexer baseLexer = new MatcherLexer();
        Assert.assertEquals(
                Collections.singletonList(TokenImpl.forFixedToken(TokenType.EOF, 1, 1)),
                baseLexer.lex("")
        );
    }

    @Test
    public void lexerWithMatchersShouldReturnEmptyWhenGivenEmptyString() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        Automata newLineAutomata = new AutomataFactory().singleCharAutomata('\n');
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher newLineMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, newLineAutomata);
        Lexer lexer = new MatcherLexer(letMatcher, newLineMatcher);
        Assert.assertEquals(
                Collections.singletonList(TokenImpl.forFixedToken(TokenType.EOF, 1, 1)),
                lexer.lex("")
        );
    }
}