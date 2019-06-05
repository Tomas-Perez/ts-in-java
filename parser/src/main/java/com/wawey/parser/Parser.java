package com.wawey.parser;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.List;

public interface Parser {
    ASTNode parse(List<Token> tokens);
}
