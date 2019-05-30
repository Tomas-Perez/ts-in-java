package com.wawey.lexer;

import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public interface Lexer {
    List<Token> lex(String input);
}
