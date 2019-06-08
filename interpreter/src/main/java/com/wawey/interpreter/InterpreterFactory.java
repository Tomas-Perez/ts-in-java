package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class InterpreterFactory {
    private InterpreterFactory() {
    }

    public static Interpreter getTSInterpreter(Printer printer) {
        return new InterpreterImpl(printer);
    }
}
