package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

public class AutomataTokenMatcherTest {
    @Test
    public void letMatcherShouldMatchLetToken() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        BasicToken expected = new BasicTokenImpl(TokenType.LET, "let");
        matcher.match('l');
        matcher.match('e');
        matcher.match('t');
        Assert.assertEquals(expected, matcher.getBasicToken());
    }

    @Test
    public void letMatcherShouldMatchLetTokenAndNoMore() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        BasicToken expected = new BasicTokenImpl(TokenType.LET, "let");
        matcher.match('l');
        matcher.match('e');
        matcher.match('t');
        matcher.match('t');
        matcher.match('e');
        matcher.match('l');
        Assert.assertEquals(expected, matcher.getBasicToken());
    }

    @Test(expected = NoMatchException.class)
    public void shouldThrowNoMatchExceptionWhenGettingUncompleteToken() {
        Automata letAutomata = new AutomataFactory().automataFor("let");
        TokenMatcher matcher = new AutomataTokenMatcher(TokenType.LET, letAutomata);
        matcher.getBasicToken();
    }
}
