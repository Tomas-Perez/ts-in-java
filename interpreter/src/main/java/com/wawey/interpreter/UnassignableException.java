package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class UnassignableException extends RuntimeException {
    public UnassignableException(VariableType valueType, VariableType variableType) {
        super(String.format("\"%s\" value is not assignable to \"%s\"", valueType, variableType));
    }
}
