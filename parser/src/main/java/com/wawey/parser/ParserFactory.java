package com.wawey.parser;

import com.wawey.parser.automata.FileAutomata;

/**
 * @author Tomas Perez Molina
 */
public class ParserFactory {
    public static Parser getTSParser() {
        return new AutomataParser(new FileAutomata());
    }
}
