package com.tribanco.recomendaai.exceptions.customExceptions;

public class EmptyResultApiException extends Exception {
    public EmptyResultApiException() { super(); }
    public EmptyResultApiException(String message) { super(message); }
    public EmptyResultApiException(String message, Throwable cause) { super(message, cause); }
    public EmptyResultApiException(Throwable cause) { super(cause); }
}

