package com.wawey.lexer;

import java.util.Arrays;
import java.util.LinkedList;
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
        List<Token> result = new LinkedList<>();
        int line = 1;
        int startColumn = 1;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
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
                Token token = buildToken(line, startColumn, alreadyMatching);
                switch (token.getType()) {
                    case SPACE:
                        startColumn += token.getLexeme().length();
                        break;
                    case NEWLINE:
                        line++;
                        startColumn = 1;
                        break;
                    default:
                        result.add(token);
                        startColumn += token.getLexeme().length();
                        break;
                }
                i--;
            }
        }
        List<TokenMatcher> alreadyMatching = matchers.stream()
                .filter(TokenMatcher::isMatching)
                .collect(Collectors.toList());
        if (alreadyMatching.size() > 0) {
            Token token = buildToken(line, startColumn, alreadyMatching);
            if (token.getType() != TokenType.SPACE && token.getType() != TokenType.NEWLINE) {
                result.add(token);
            }
        }
        return result;
    }

    private Token buildToken(int line, int startColumn, List<TokenMatcher> alreadyMatching) {
        TokenMatcher matcher = alreadyMatching.get(0);
        BasicToken basicToken = matcher.getBasicToken();
        matchers.forEach(TokenMatcher::reset);
        return new TokenImpl(basicToken, startColumn, line);
    }
}
