package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public interface StateChange {
    ParserAutomataState getNextState();
    ImmutableStack<ASTNode> getNewStack();
}
