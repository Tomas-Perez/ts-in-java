package com.wawey.lexer;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LexerBuilder {
    private static final AutomataFactory factory = new AutomataFactory();

    public static Lexer buildTSLexer() {
        List<TokenMatcher> keywordMatchers =
                Arrays.stream(TokenType.values())
                        .filter(TokenType::isFixed)
                        .map(t -> new AutomataTokenMatcher(t, factory.automataFor(t.getLexeme())))
                        .collect(Collectors.toList());
        TokenMatcher idMatcher = new AutomataTokenMatcher(
                TokenType.IDENTIFIER,
                new LinkedAutomata.Builder()
                        .andThen(factory.infiniteRegexAutomata("[a-zA-z]"))
                        .maybeThen(factory.infiniteRegexAutomata("[a-zA-Z0-9]"))
                        .build()
        );
        TokenMatcher numLiteralMatcher1 = new AutomataTokenMatcher(
                TokenType.NUMBER_LITERAL,
                new LinkedAutomata.Builder()
                        .andThen(factory.infiniteRegexAutomata("[1-9]"))
                        .maybeThen(factory.infiniteRegexAutomata("[0-9]"))
                        .maybeThen(factory.singleCharAutomata('.'))
                        .maybeThen(factory.infiniteRegexAutomata("[0-9]"))
                        .build()
        );
        TokenMatcher numLiteralMatcher2 = new AutomataTokenMatcher(
                TokenType.NUMBER_LITERAL,
                new LinkedAutomata.Builder()
                        .andThen(factory.singleCharAutomata('0'))
                        .maybeThen(factory.singleCharAutomata('.'))
                        .maybeThen(factory.infiniteRegexAutomata("[0-9]"))
                        .build()
        );
        TokenMatcher singleQuoteStrLiteralMatcher = new AutomataTokenMatcher(TokenType.STRING_LITERAL, factory.delimitedWordAutomata('"', Arrays.asList('\n', '\r')));
        TokenMatcher doubleQuoteStrLiteralMatcher = new AutomataTokenMatcher(TokenType.STRING_LITERAL, factory.delimitedWordAutomata('\'', Arrays.asList('\n', '\r')));
        TokenMatcher spaceMatcher = new AutomataTokenMatcher(TokenType.SPACE, factory.infiniteRegexAutomata(" "));
        TokenMatcher crlfMatcher = new AutomataTokenMatcher(
                TokenType.NEWLINE,
                new LinkedAutomata.Builder()
                        .andThen(factory.singleCharAutomata('\r'))
                        .andThen(factory.singleCharAutomata('\n'))
                        .build()
        );
        TokenMatcher lfMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, factory.singleCharAutomata('\n'));

        List<TokenMatcher> matchers =
                ImmutableList.<TokenMatcher>builder()
                        .addAll(keywordMatchers)
                        .add(idMatcher)
                        .add(numLiteralMatcher1)
                        .add(numLiteralMatcher2)
                        .add(singleQuoteStrLiteralMatcher)
                        .add(doubleQuoteStrLiteralMatcher)
                        .add(spaceMatcher)
                        .add(crlfMatcher)
                        .add(lfMatcher)
                        .build();

        return new MatcherLexer(matchers);
    }
}
