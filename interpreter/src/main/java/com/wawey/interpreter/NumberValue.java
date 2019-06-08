package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class NumberValue implements Value{
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }

    @Override
    public VariableType getType() {
        return VariableType.NUMBER;
    }

    @Override
    public String getString() {
        return value + "";
    }

    @Override
    public Value sum(Value other) {
        if (other.getType() == VariableType.NUMBER) {
            return new NumberValue(value + other.getNumber());
        } else {
            return new StringValue(value + other.getString());
        }
    }

    @Override
    public Value subtract(Value other) {
        return new NumberValue(value - other.getNumber());
    }

    @Override
    public Value multiply(Value other) {
        return new NumberValue(value * other.getNumber());
    }

    @Override
    public Value divide(Value other) {
        return new NumberValue(value / other.getNumber());
    }

    @Override
    public double getNumber() {
        return value;
    }
}
