package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class ReferenceError extends RuntimeException {
    public ReferenceError(String identifier) {
        super(identifier + " is not defined");
    }
}
