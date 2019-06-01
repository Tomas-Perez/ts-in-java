package com.wawey.lexer;

import java.util.Objects;

public class TokenImpl implements Token {
    private final TokenType type;
    private final String lexeme;
    private final int line;
    private final int startColumn;

    public TokenImpl(BasicToken basicToken, int line, int startColumn) {
        this.type = basicToken.getType();
        this.lexeme = basicToken.getLexeme();
        this.startColumn = startColumn;
        this.line = line;
    }

    public TokenImpl(TokenType type, String lexeme, int line, int startColumn) {
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

    @Override
    public String toString() {
        return String.format("Token(type=%s, lexeme=%s, line=%d, col=%d)", type, lexeme, line, startColumn);
    }

    public static TokenImpl forFixedToken(TokenType type, int line, int startColumn) {
        if (!type.isFixed()) throw new IllegalStateException(type.toString() + " is not a fixed token");
        return new TokenImpl(type, type.getLexeme(), line, startColumn);
    }
}
