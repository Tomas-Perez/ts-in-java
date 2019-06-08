package com.wawey.interpreter;

/**
 * @author Tomas Perez Molina
 */
public class StandardOutPrinter implements Printer {
    @Override
    public void print(String input) {
        System.out.println(input);
    }
}
