package com.wawey.parser;

import java.util.Objects;

public class LineColumnRangePairImpl implements LineColumnRangePair {
    private final int line;
    private final Range columnRange;

    public LineColumnRangePairImpl(int line, Range columnRange) {
        this.line = line;
        this.columnRange = columnRange;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public Range getColumnRange() {
        return columnRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineColumnRangePairImpl that = (LineColumnRangePairImpl) o;
        return line == that.line &&
                Objects.equals(columnRange, that.columnRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, columnRange);
    }

    @Override
    public String toString() {
        return String.format("LineColumnRangePair(%d, %s)", line, columnRange);
    }
}
