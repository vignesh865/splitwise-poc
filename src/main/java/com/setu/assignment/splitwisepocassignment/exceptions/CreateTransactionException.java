package com.setu.assignment.splitwisepocassignment.exceptions;

public class CreateTransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CreateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateTransactionException(String message) {
        super(message);
    }

}
