package com.wawey.parser.ast;

public interface ASTVisitor<R> {
    R visit(NonTerminalNode nonTerminal);
    R visit(TerminalNode terminal);
}
