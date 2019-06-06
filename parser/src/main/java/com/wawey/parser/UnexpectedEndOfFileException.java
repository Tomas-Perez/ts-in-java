package com.wawey.parser;

public class UnexpectedEndOfFileException extends RuntimeException {
    public UnexpectedEndOfFileException() {
        super("Unexpected end of file");
    }
}
