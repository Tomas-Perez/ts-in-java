package com.wawey.lexer;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatcherLexer implements Lexer {
    private final List<TokenMatcher> matchers;

    public MatcherLexer(TokenMatcher... matchers) {
        this.matchers = Arrays.asList(matchers);
    }

    public MatcherLexer(List<TokenMatcher> matchers) {
        this.matchers = matchers;
    }

    @Override
    public List<Token> lex(String input) {
        LexerState state = LexerState.initialState();
        for (char c : input.toCharArray()) {
            boolean tryAgain;
            do {
                tryAgain = false;
                List<TokenMatcher> alreadyMatching = getAlreadyMatching();
                List<TokenMatcher> matchersForChar = alreadyMatching.stream()
                        .filter(m -> m.match(c))
                        .collect(Collectors.toList());
                if (matchersForChar.size() == 0 && alreadyMatching.size() == 0) {
                    throw new LexicalError("Unknown character: " + c);
                } else if (matchersForChar.size() == 0) {
                    Token token = buildToken(state.line, state.column, alreadyMatching);
                    state = state.addToken(token);
                    tryAgain = true;
                }
            } while (tryAgain);
        }
        List<TokenMatcher> alreadyMatching = matchers.stream()
                .filter(TokenMatcher::isMatching)
                .collect(Collectors.toList());
        if (alreadyMatching.size() > 0) {
            Token token = buildToken(state.line, state.column, alreadyMatching);
            state = state.addToken(token);
        }
        return state.result;
    }

    private List<TokenMatcher> getAlreadyMatching() {
        List<TokenMatcher> alreadyMatching = matchers.stream()
                .filter(TokenMatcher::isMatching)
                .collect(Collectors.toList());
        if (alreadyMatching.size() == 0) alreadyMatching = matchers;
        return alreadyMatching;
    }

    private Token buildToken(int line, int startColumn, List<TokenMatcher> alreadyMatching) {
        TokenMatcher matcher = alreadyMatching.get(0);
        BasicToken basicToken = matcher.getBasicToken();
        matchers.forEach(TokenMatcher::reset);
        return new TokenImpl(basicToken, startColumn, line);
    }

    private static class LexerState {
        final int line;
        final int column;
        final List<Token> result;

        LexerState(int line, int column, List<Token> result) {
            this.line = line;
            this.column = column;
            this.result = result;
        }

        LexerState addToken(Token token) {
            switch (token.getType()) {
                case SPACE:
                    return new LexerState(line, column + token.getLexeme().length(), result);
                case NEWLINE:
                    return new LexerState(line + 1, 1, result);
                default:
                    final ImmutableList<Token> newResult =
                            ImmutableList.<Token>builder()
                                    .addAll(result)
                                    .add(token)
                                    .build();
                    return new LexerState(line, column + token.getLexeme().length(), newResult);
            }
        }

        static LexerState initialState() {
            return new LexerState(1, 1, ImmutableList.of());
        }
    }
}
