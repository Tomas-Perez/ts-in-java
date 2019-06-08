package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class NotConvertibleToNumberException extends RuntimeException{
    public NotConvertibleToNumberException(String value) {
        super(value + " is not convertible to number");
    }
}
