package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public interface VariableDeclarator {
    void declareVariable(String identifier, VariableType type);
    default void declareVariable(String identifier) {
        declareVariable(identifier, VariableType.ANY);
    }
    void setVariableValue(String identifier, Value value);
}
