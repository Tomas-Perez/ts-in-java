package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

public class TransitionImpl implements Transition {


    @Override
    public boolean consumes(Token token) {
        return false;
    }

    @Override
    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
        return null;
    }
}
