package com.wawey.lexer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LexerImpl implements Lexer {
    private final List<TokenMatcher> matchers;

    public LexerImpl(TokenMatcher... matchers) {
        this.matchers = Arrays.asList(matchers);
    }

    @Override
    public List<Token> lex(String input) {
        List<Token> result = new LinkedList<>();
        int line = 0;
        int startColumn = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\n') {
                line++;
                continue;
            }
            if (c == ' ') {
                startColumn++;
                continue;
            }
            List<TokenMatcher> alreadyMatching = matchers.stream()
                    .filter(TokenMatcher::isMatching)
                    .collect(Collectors.toList());
            if (alreadyMatching.size() == 0) {
                alreadyMatching = matchers;
            }
            List<TokenMatcher> matchedCurrentChar = alreadyMatching.stream()
                    .filter(m -> m.match(c))
                    .collect(Collectors.toList());
            if (matchedCurrentChar.size() == 0 && alreadyMatching.size() == 0) {
                throw new LexicalError("Unknown character: " + c);
            } else if (matchedCurrentChar.size() == 0) {
                TokenMatcher matcher = alreadyMatching.get(0);
                BasicToken basicToken = matcher.getBasicToken();
                result.add(new TokenImpl(basicToken, startColumn, line));
                matchers.forEach(TokenMatcher::reset);
                startColumn += basicToken.getLexeme().length();
                i--;
            }
        }
        List<TokenMatcher> alreadyMatching = matchers.stream()
                .filter(TokenMatcher::isMatching).collect(Collectors.toList());
        if (alreadyMatching.size() > 0){
            TokenMatcher matcher = alreadyMatching.get(0);
            BasicToken basicToken = matcher.getBasicToken();
            result.add(new TokenImpl(basicToken, startColumn, line));
            matchers.forEach(TokenMatcher::reset);
        }
        return result;
    }
}
