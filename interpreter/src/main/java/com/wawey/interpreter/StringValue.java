package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class StringValue implements Value{
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public VariableType getType() {
        return VariableType.STRING;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public Value sum(Value other) {
        return new StringValue(value + other.getString());
    }

    @Override
    public Value subtract(Value other) {
        throw new NotConvertibleToNumberException(value);
    }

    @Override
    public Value multiply(Value other) {
        throw new NotConvertibleToNumberException(value);
    }

    @Override
    public Value divide(Value other) {
        throw new NotConvertibleToNumberException(value);
    }
}
