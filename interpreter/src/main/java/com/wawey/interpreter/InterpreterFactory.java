package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class InterpreterFactory {

    public Interpreter getTSInterpreter(Printer printer) {
        return new InterpreterImpl(printer);
    }
}
