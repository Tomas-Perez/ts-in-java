package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NumberTypeNode;
import com.wawey.parser.ast.TypeNode;

import java.util.Stack;

public class NumberTypeTransition implements Transition {
    @Override
    public boolean consumes(Token token) {
        return token.getType() == TokenType.NUMBER_TYPE;
    }

    @Override
    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
        stack.push(
                new TypeNode(
                        new NumberTypeNode(token.getLine(), token.getStartColumn())
                )
        );
        return null;
    }
}
