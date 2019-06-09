package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class LocatedInterpreterException extends RuntimeException{
    public LocatedInterpreterException(InterpreterException exc, int line, int column) {
        super(String.format("%s at line: %d, column: %d", exc.getMessage(), line, column));
    }
}
