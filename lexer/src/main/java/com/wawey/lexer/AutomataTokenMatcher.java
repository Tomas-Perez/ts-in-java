package com.wawey.lexer;

public class AutomataTokenMatcher implements TokenMatcher {
    private final Automata automata;
    private final TokenType tokenType;
    private String match = "";

    public AutomataTokenMatcher(TokenType tokenType, Automata automata) {
        this.automata = automata;
        this.tokenType = tokenType;
    }

    @Override
    public int matchLength() {
        return match.length();
    }

    @Override
    public boolean isMatching() {
        return matchLength() > 0;
    }

    @Override
    public boolean match(char c) {
        try {
            automata.consume(c);
            match = match + c;
            return true;
        } catch (NoTransitionException exc) {
            return false;
        }
    }

    @Override
    public BasicToken getBasicToken() {
        if (automata.acceptable()) {
            return new BasicToken(tokenType, match);
        } else {
            throw new NoMatchException(tokenType, match);
        }
    }

    @Override
    public void reset() {
        match = "";
        automata.reset();
    }
}
