package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class ReferenceError extends InterpreterException {
    public ReferenceError(String identifier) {
        super(String.format("\"%s\" is not defined", identifier));
    }
}
