package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class NotConvertibleToNumberException extends InterpreterException{
    public NotConvertibleToNumberException(String value) {
        super(String.format("\"%s\" is not convertible to number", value));
    }
}
