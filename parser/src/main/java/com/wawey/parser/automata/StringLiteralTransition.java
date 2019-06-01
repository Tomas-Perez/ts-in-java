package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.LiteralNode;
import com.wawey.parser.ast.StringLiteralNode;

import java.util.Stack;

public class StringLiteralTransition implements Transition {
    @Override
    public boolean consumes(Token token) {
        return token.getType() == TokenType.STRING_LITERAL;
    }

    @Override
    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
        stack.push(
                new LiteralNode(
                        new StringLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
                )
        );
        return null;
    }
}
