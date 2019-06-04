package com.wawey.parser.ast;

import com.wawey.parser.LineColumnRangePair;
import com.wawey.parser.Range;
import com.wawey.parser.Rule;

import java.util.*;
import java.util.stream.Collectors;

public class NonTerminalNode implements ASTNode {
    private final Rule rule;
    private final List<ASTNode> children;

    public NonTerminalNode(Rule rule, List<ASTNode> children) {
        if (children.size() == 0)
            throw new IllegalStateException("Non terminal node must have at least one child");
        this.rule = rule;
        this.children = children;
    }

    public NonTerminalNode(Rule rule, ASTNode child) {
        this(rule, Collections.singletonList(child));
    }

    public NonTerminalNode(Rule rule, ASTNode... children) {
        this(rule, Arrays.asList(children));
    }

    public Rule getRule() {
        return rule;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public int getStartLine() {
        return children.get(0).getStartLine();
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<LineColumnRangePair> getColumnRanges() {
        Map<Integer, List<LineColumnRangePair>> byLine =
                children.stream()
                        .flatMap(n -> n.getColumnRanges().stream())
                        .collect(Collectors.groupingBy(LineColumnRangePair::getLine));
        return byLine.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(ps -> unifyRanges(ps.getKey(), ps.getValue()))
                .collect(Collectors.toList());
    }

    private LineColumnRangePair unifyRanges(int line, List<LineColumnRangePair> ranges) {
        return ranges.stream()
                .reduce((r1, r2) ->
                        new LineColumnRangePair(
                                line,
                                new Range(r1.getColumnRange().getStart(), r2.getColumnRange().getEnd())
                        ))
                .get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonTerminalNode that = (NonTerminalNode) o;
        return rule == that.rule &&
                Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, children);
    }

    @Override
    public String toString() {
        return "NonTerminalNode{" +
                "rule=" + rule +
                ", children=" + children +
                '}';
    }
}
