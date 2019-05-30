package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LexerImplTest {
    @Test
    public void letLexerShouldDetectSingleLetToken() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        Lexer letLexer = new LexerImpl(matcher);
        List<Token> expected = Collections.singletonList(new TokenImpl(TokenType.LET, "let", 0, 0));
        Assert.assertEquals(expected, letLexer.lex("let"));
    }

    @Test
    public void letLexerShouldDetectMultipleLetTokens() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        Lexer letLexer = new LexerImpl(matcher);
        List<Token> expected = Arrays.asList(
                new TokenImpl(TokenType.LET, "let", 0, 0),
                new TokenImpl(TokenType.LET, "let", 3, 0),
                new TokenImpl(TokenType.LET, "let", 6, 0),
                new TokenImpl(TokenType.LET, "let", 9, 0),
                new TokenImpl(TokenType.LET, "let", 12, 0)
        );
        Assert.assertEquals(expected, letLexer.lex("letletletletlet"));
    }
}