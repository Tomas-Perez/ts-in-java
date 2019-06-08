package com.wawey.lexer;

import java.util.Objects;

/**
 * @author Tomas Perez Molina
 */
public class BasicTokenImpl implements BasicToken {
    private TokenType type;
    private String lexeme;

    public BasicTokenImpl(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String getLexeme() {
        return lexeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicTokenImpl that = (BasicTokenImpl) o;
        return type == that.type &&
                lexeme.equals(that.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme);
    }
}

