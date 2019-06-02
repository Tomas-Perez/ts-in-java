package com.wawey.parser.automata;

import com.wawey.lexer.TokenImpl;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.NumberTypeNode;
import com.wawey.parser.ast.StringTypeNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Perez Molina
 */
public class TypeAnnotationAutomataTest {
    @Test
    public void shouldBuildTreeOf_TypeAnnotation_Type_StringType_WhenGivenTokensForStringTypeAnnotation() {
        ParserAutomata automata = new TypeAnnotationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.STRING_TYPE, 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.TYPE_ANNOTATION,
                new NonTerminalNode(
                        Rule.TYPE,
                        new StringTypeNode(1, 2)
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }

    @Test
    public void shouldBuildTreeOf_TypeAnnotation_Type_NumberType_WhenGivenTokensForNumberTypeAnnotation() {
        ParserAutomata automata = new TypeAnnotationAutomata();
        automata.consume(TokenImpl.forFixedToken(TokenType.COLON, 1, 1));
        automata.consume(TokenImpl.forFixedToken(TokenType.NUMBER_TYPE, 1, 2));
        Assert.assertTrue(automata.acceptable());
        ASTNode expected = new NonTerminalNode(
                Rule.TYPE_ANNOTATION,
                new NonTerminalNode(
                        Rule.TYPE,
                        new NumberTypeNode(1, 2)
                )
        );
        Assert.assertEquals(expected, automata.getResult());
    }
}
