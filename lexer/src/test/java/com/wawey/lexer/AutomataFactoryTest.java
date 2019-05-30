package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

public class AutomataFactoryTest {

    @Test
    public void automataForLetShouldAcceptWhenGivenLET() {
        AutomataFactory factory = new AutomataFactory();
        Automata automata = factory.automataFor("let");
        automata.consume('l');
        automata.consume('e');
        automata.consume('t');
        Assert.assertTrue(automata.acceptable());
    }

    @Test
    public void automataForLetShouldNotAcceptWhenGivenLE() {
        AutomataFactory factory = new AutomataFactory();
        Automata automata = factory.automataFor("let");
        automata.consume('l');
        automata.consume('e');
        Assert.assertFalse(automata.acceptable());
    }

    @Test(expected = NoTransitionException.class)
    public void lAutomataShouldFailWhenGivenX() {
        AutomataFactory factory = new AutomataFactory();
        Automata automata = factory.automataFor("l");
        automata.consume('x');
    }
}