package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.StringTypeNode;
import com.wawey.parser.ast.TypeNode;

import java.util.Stack;

public class StringTypeTransition implements Transition {
    @Override
    public boolean consumes(Token token) {
        return token.getType() == TokenType.STRING_TYPE;
    }

    @Override
    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
        stack.push(
                new TypeNode(
                        new StringTypeNode(token.getLine(), token.getStartColumn())
                )
        );
        return null;
    }
}
