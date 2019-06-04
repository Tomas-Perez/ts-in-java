package com.wawey.parser.ast;

import com.wawey.parser.LineColumnRangePair;
import com.wawey.parser.Range;
import com.wawey.parser.Rule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TerminalNode implements ASTNode {
    private final String value;
    private final Rule rule;
    private final LineColumnRangePair lineColumnRangePair;

    public TerminalNode(Rule rule, int line, int startColumn, String value) {
        this.rule = rule;
        this.value = value;
        this.lineColumnRangePair = new LineColumnRangePair(line, new Range(startColumn, startColumn + value.length()));
    }

    @Override
    public int getStartLine() {
        return lineColumnRangePair.getLine();
    }

    @Override
    public List<LineColumnRangePair> getColumnRanges() {
        return Collections.singletonList(lineColumnRangePair);
    }

    public String getValue() {
        return value;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminalNode that = (TerminalNode) o;
        return Objects.equals(value, that.value) &&
                rule == that.rule &&
                Objects.equals(lineColumnRangePair, that.lineColumnRangePair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, rule, lineColumnRangePair);
    }

    @Override
    public String toString() {
        return "TerminalNode{" +
                "value='" + value + '\'' +
                ", rule=" + rule +
                ", lineColumnRangePair=" + lineColumnRangePair +
                '}';
    }
}
