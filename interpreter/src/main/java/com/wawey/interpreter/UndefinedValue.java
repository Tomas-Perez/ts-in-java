package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class UndefinedValue implements Value{
    private final String value = "undefined";

    private UndefinedValue() {}

    @Override
    public VariableType getType() {
        return VariableType.ANY;
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

    private static UndefinedValue instance = new UndefinedValue();

    public static UndefinedValue getInstance() {
        return instance;
    }
}
