package com.wawey.parser.ast;

import com.wawey.parser.LineColumnRangePair;
import com.wawey.parser.Range;
import com.wawey.parser.Rule;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ASTNodeTest {
    @Test
    public void terminalRangeShouldEncompassItsValue() {
        ASTNode terminal = new TerminalNode(anyRule(), 1, 1, "ads");
        Assert.assertEquals(
                Collections.singletonList(new LineColumnRangePair(1, new Range(1, 4))),
                terminal.getColumnRanges()
        );
    }

    @Test
    public void nonTerminalRangeShouldIncludeChildrenRangesOnSameLine() {
        ASTNode terminal1 = new TerminalNode(anyRule(), 1, 1, "ads");
        ASTNode terminal2 = new TerminalNode(anyRule(), 1, 5, "ads");
        ASTNode nonTerminal = new NonTerminalNode(anyRule(), Arrays.asList(terminal1, terminal2));
        Assert.assertEquals(
                Collections.singletonList(new LineColumnRangePair(1, new Range(1, 8))),
                nonTerminal.getColumnRanges()
        );
    }

    @Test
    public void nonTerminalShouldIncludeChildrenRangesOnMultipleLines() {
        ASTNode terminal1 = new TerminalNode(anyRule(), 1, 1, "ads");
        ASTNode terminal2 = new TerminalNode(anyRule(), 2, 1, "ads");
        ASTNode nonTerminal = new NonTerminalNode(anyRule(), Arrays.asList(terminal1, terminal2));
        Assert.assertEquals(
                Arrays.asList(
                        new LineColumnRangePair(1, new Range(1, 4)),
                        new LineColumnRangePair(2, new Range(1, 4))
                ),
                nonTerminal.getColumnRanges()
        );
    }

    private Rule anyRule() {
        return Rule.NUMBER_LITERAL;
    }
}