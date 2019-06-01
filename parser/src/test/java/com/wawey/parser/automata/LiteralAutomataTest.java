package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.LiteralNode;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberLiteralNode;
import com.wawey.parser.ast.StringLiteralNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class LiteralAutomataTest {

    @Test
    public void shouldBuildATreeOf_Literal_NumberLiteral_WhenGivenANumberLiteralToken() {
        ParserAutomata parserAutomata = new LiteralAutomata();
        parserAutomata.consume(new TokenImpl(TokenType.NUMBER_LITERAL, "12", 1, 1));
        Assert.assertTrue(parserAutomata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(Rule.LITERAL, Collections.singletonList(new NumberLiteralNode(1, 1, "12"))),
                parserAutomata.getResult()
        );
    }

    @Test
    public void shouldBuildATreeOf_Literal_StringLiteral_WhenGivenAStringLiteralToken() {
        ParserAutomata parserAutomata = new LiteralAutomata();
        parserAutomata.consume(new TokenImpl(TokenType.STRING_LITERAL, "\"12\"", 1, 1));
        Assert.assertTrue(parserAutomata.acceptable());
        Assert.assertEquals(
                new NonTerminalNode(Rule.LITERAL, Collections.singletonList(new StringLiteralNode(1, 1, "\"12\""))),
                parserAutomata.getResult()
        );
    }

}