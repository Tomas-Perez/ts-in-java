package com.wawey.parser;

public class UnexpectedEndOfFileException extends RuntimeException {
    public UnexpectedEndOfFileException(int line, int column) {
        super(
                String.format(
                        "Unexpected end of file at line %d, column %d",
                        line,
                        column
                )
        );
    }
}
