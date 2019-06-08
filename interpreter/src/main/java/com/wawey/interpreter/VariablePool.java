package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public interface VariablePool {
    Value getVariable(String identifier);
}
