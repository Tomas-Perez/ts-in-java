package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class UnassignableException extends InterpreterException {
    public UnassignableException(VariableType valueType, VariableType variableType) {
        super(String.format("\"%s\" value is not assignable to \"%s\"", valueType, variableType));
    }
}
