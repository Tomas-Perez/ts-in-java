package com.wawey.parser.ast;

import com.wawey.parser.LineColumnRangePair;
import com.wawey.parser.Rule;

import java.util.List;

public interface ASTNode {
    Rule getRule();
    int getStartLine();
    List<LineColumnRangePair> getColumnRanges();
}
