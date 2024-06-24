package com.example.book.exception;

import lombok.Getter;

@Getter
public class NoResourceFoundException extends Exception {

    private final String resType;
    private final String predicate;

    public NoResourceFoundException (String type, String predicate) {
        super("Cannot find " + type + " that matches " + predicate);
        this.resType = type;
        this.predicate = predicate;
    }
}
