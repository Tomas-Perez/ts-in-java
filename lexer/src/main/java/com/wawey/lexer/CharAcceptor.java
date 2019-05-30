package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
@FunctionalInterface
public interface CharAcceptor {
    boolean accepts(char c);
}
