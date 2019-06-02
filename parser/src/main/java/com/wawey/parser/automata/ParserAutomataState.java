package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

public interface ParserAutomataState {
    StateChange transition(Token token, Stack<ASTNode> stack);
    boolean isAcceptable();
    boolean accepts(Token token);
}
