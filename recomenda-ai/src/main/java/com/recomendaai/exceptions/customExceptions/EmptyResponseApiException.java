package com.recomendaai.exceptions.customExceptions;

public class EmptyResponseApiException extends Exception {
    public EmptyResponseApiException() { super(); }
    public EmptyResponseApiException(String message) { super(message); }
    public EmptyResponseApiException(String message, Throwable cause) { super(message, cause); }
    public EmptyResponseApiException(Throwable cause) { super(cause); }
}

