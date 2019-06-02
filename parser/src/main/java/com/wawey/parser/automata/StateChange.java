package com.wawey.parser.automata;

import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public interface StateChange {
    ParserAutomataState getNextState();
    Stack<ASTNode> getNewStack();
}
