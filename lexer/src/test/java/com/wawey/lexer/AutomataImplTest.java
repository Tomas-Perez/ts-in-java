package com.wawey.lexer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Perez Molina
 */
public class AutomataImplTest {

    @Test
    public void letAutomataShouldAcceptWhenGivenLET() {
        AutomataState letConsumed = AutomataStateImpl.acceptanceState();
        AutomataState leConsumed = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('t'), () -> letConsumed));
        AutomataState lConsumed = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('e'), () -> leConsumed));
        AutomataState initial = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('l'), () -> lConsumed));
        Automata letAutomata = new AutomataImpl(initial);
        letAutomata.consume('l');
        letAutomata.consume('e');
        letAutomata.consume('t');
        Assert.assertTrue(letAutomata.acceptable());
    }

    @Test
    public void letAutomataShouldNotAcceptWhenGivenLE() {
        AutomataState letConsumed = AutomataStateImpl.acceptanceState();
        AutomataState leConsumed = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('t'), () -> letConsumed));
        AutomataState lConsumed = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('e'), () -> leConsumed));
        AutomataState initial = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('l'), () -> lConsumed));
        Automata letAutomata = new AutomataImpl(initial);
        letAutomata.consume('l');
        letAutomata.consume('e');
        Assert.assertFalse(letAutomata.acceptable());
    }

    @Test(expected = NoTransitionException.class)
    public void lAutomataShouldFailWhenGivenX() {
        AutomataState lConsumed = AutomataStateImpl.acceptanceState();
        AutomataState initial = AutomataStateImpl.intermediateState(new Transition(new SingleCharAcceptor('l'), () -> lConsumed));
        Automata lAutomata = new AutomataImpl(initial);
        lAutomata.consume('x');
    }
}
