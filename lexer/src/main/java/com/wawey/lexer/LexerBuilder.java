package com.wawey.lexer;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LexerBuilder {
    private static final AutomataFactory factory = new AutomataFactory();

    static Lexer buildTSLexer() {
        List<TokenMatcher> keywordMatchers =
                Arrays.stream(TokenType.values())
                        .filter(TokenType::isFixed)
                        .map(t -> new BasicToken(t, t.getLexeme()))
                        .map(t -> new AutomataTokenMatcher(
                                t.getType(),
                                factory.automataFor(t.getLexeme())
                        ))
                        .collect(Collectors.toList());
        TokenMatcher idMatcher = new AutomataTokenMatcher(
                TokenType.IDENTIFIER,
                new LinkedAutomata.Builder()
                        .andThen(factory.infiniteRegexAutomata("[a-zA-z]"))
                        .maybeThen(factory.infiniteRegexAutomata("[a-zA-Z0-9]"))
                        .build()
        );
        TokenMatcher numLiteralMatcher = new AutomataTokenMatcher(TokenType.NUMBER_LITERAL, factory.infiniteRegexAutomata("[0-9]"));
        TokenMatcher singleQuoteStrLiteralMatcher = new AutomataTokenMatcher(TokenType.STRING_LITERAL, factory.delimitedWordAutomata('"'));
        TokenMatcher doubleQuoteStrLiteralMatcher = new AutomataTokenMatcher(TokenType.STRING_LITERAL, factory.delimitedWordAutomata('\''));
        TokenMatcher spaceMatcher = new AutomataTokenMatcher(TokenType.SPACE, factory.infiniteRegexAutomata(" "));
        TokenMatcher newLineMatcher = new AutomataTokenMatcher(TokenType.NEWLINE, factory.singleCharAutomata('\n'));

        List<TokenMatcher> matchers =
                ImmutableList.<TokenMatcher>builder()
                        .addAll(keywordMatchers)
                        .add(idMatcher)
                        .add(numLiteralMatcher)
                        .add(singleQuoteStrLiteralMatcher)
                        .add(doubleQuoteStrLiteralMatcher)
                        .add(spaceMatcher)
                        .add(newLineMatcher)
                        .build();

        return new MatcherLexer(matchers);
    }
}
