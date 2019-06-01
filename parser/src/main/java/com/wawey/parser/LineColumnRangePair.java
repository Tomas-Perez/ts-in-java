package com.wawey.parser;

import java.util.Objects;

public class LineColumnRangePair {
    private final int line;
    private final Range columnRange;

    public LineColumnRangePair(int line, Range columnRange) {
        this.line = line;
        this.columnRange = columnRange;
    }

    public int getLine() {
        return line;
    }

    public Range getColumnRange() {
        return columnRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineColumnRangePair that = (LineColumnRangePair) o;
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
