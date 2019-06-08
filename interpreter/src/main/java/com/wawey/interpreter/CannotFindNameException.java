package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class CannotFindNameException extends RuntimeException {
    public CannotFindNameException(String identifier) {
        super(String.format("Cannot find name \'%s\'", identifier));
    }
}
