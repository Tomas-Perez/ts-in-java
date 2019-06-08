package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public interface Value {
    VariableType getType();
    Value sum(Value other);
    Value subtract(Value other);
    Value multiply(Value other);
    Value divide(Value other);
    String getString();
    default double getNumber() {
        throw new NotConvertibleToNumberException(getString());
    }
}
