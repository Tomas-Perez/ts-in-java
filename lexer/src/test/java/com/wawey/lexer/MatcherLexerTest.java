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
        List<Token> expected = Collections.singletonList(new TokenImpl(TokenType.LET, "let", 1, 1));
        Assert.assertEquals(expected, letLexer.lex("let"));
    }

    @Test
    public void shouldDetectMultipleLetTokensInOneLine() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        Lexer letLexer = new MatcherLexer(matcher);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.LET, "let", 1, 1),
                new TokenImpl(TokenType.LET, "let", 4, 1),
                new TokenImpl(TokenType.LET, "let", 7, 1),
                new TokenImpl(TokenType.LET, "let", 10, 1),
                new TokenImpl(TokenType.LET, "let", 13, 1)
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
                new TokenImpl(TokenType.LET, "let", 1, 1),
                new TokenImpl(TokenType.LET, "let", 4, 1),
                new TokenImpl(TokenType.LET, "let", 1, 2),
                new TokenImpl(TokenType.LET, "let", 4, 2),
                new TokenImpl(TokenType.LET, "let", 7, 2)
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
        List<Token> expected = Collections.singletonList(new TokenImpl(TokenType.LET, "let", 1, 2));
        Assert.assertEquals(expected, letLexer.lex("\nlet\n"));
    }

    @Test
    public void shouldPrioritizeTokensByMatcherOrder() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher otherMatcher = new AutomataTokenMatcher(TokenType.IDENTIFIER, letAutomata);
        Lexer letFirstLexer = new MatcherLexer(letMatcher, otherMatcher);
        List<Token> letExpected = Collections.singletonList(new TokenImpl(TokenType.LET, "let", 1, 1));
        Assert.assertEquals(letExpected, letFirstLexer.lex("let"));

        Lexer idFirstLexer = new MatcherLexer(otherMatcher, letMatcher);
        List<Token> idExpected = Collections.singletonList(new TokenImpl(TokenType.IDENTIFIER, "let", 1, 1));
        Assert.assertEquals(idExpected, idFirstLexer.lex("let"));
    }

    @Test(expected = LexicalError.class)
    public void baseLexerShouldThrowLexicalErrorWhenGivenAnything() {
        Lexer baseLexer = new MatcherLexer();
        baseLexer.lex("a");
    }

    @Test
    public void baseLexerShouldReturnEmptyWhenGivenEmptyString() {
        Lexer baseLexer = new MatcherLexer();
        Assert.assertEquals(Collections.emptyList(), baseLexer.lex(""));
    }

    @Test
    public void lexerWithMatchersShouldReturnEmptyWhenGivenEmptyString() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        Automata newLineAutomata = new AutomataFactory().singleCharAutomata('\n');
        TokenMatcher letMatcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        TokenMatcher newLineMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, newLineAutomata);
        Lexer lexer = new MatcherLexer(letMatcher, newLineMatcher);
        Assert.assertEquals(Collections.emptyList(), lexer.lex(""));
    }
}