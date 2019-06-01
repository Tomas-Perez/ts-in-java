package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NumberLiteralNode;

import java.util.Stack;

public class NumberLiteralTransition implements Transition {
    @Override
    public boolean consumes(Token token) {
        return token.getType() == TokenType.NUMBER_LITERAL;
    }

    @Override
    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
        stack.push(
                new NumberLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
        return null;
    }
}
