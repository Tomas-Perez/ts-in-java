package com.wawey.lexer;

import java.util.Objects;

public class TokenImpl implements Token {
    private final TokenType type;
    private final String lexeme;
    private final int startColumn;
    private final int line;

    public TokenImpl(BasicToken basicToken, int startColumn, int line) {
        this.type = basicToken.getType();
        this.lexeme = basicToken.getLexeme();
        this.startColumn = startColumn;
        this.line = line;
    }

    public TokenImpl(TokenType type, String lexeme, int startColumn, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.startColumn = startColumn;
        this.line = line;
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
    public int getStartColumn() {
        return startColumn;
    }

    @Override
    public int getEndColumn() {
        return startColumn + lexeme.length();
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenImpl token = (TokenImpl) o;
        return startColumn == token.startColumn &&
                line == token.line &&
                type == token.type &&
                Objects.equals(lexeme, token.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme, startColumn, line);
    }
}
