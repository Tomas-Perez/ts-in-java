package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class RedeclarationError extends RuntimeException {
    public RedeclarationError(String identifier) {
        super(String.format("Redeclaration of let \"%s\"", identifier));
    }
}
