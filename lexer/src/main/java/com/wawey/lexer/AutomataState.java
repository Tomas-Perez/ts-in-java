package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public interface AutomataState {
    boolean isAcceptable();
    AutomataState transition(char c);
}
