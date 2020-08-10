package com.recomendaai.exceptions.customExceptions;

public class EmptyResponseException extends Exception {
    public EmptyResponseException() { super(); }
    public EmptyResponseException(String message) { super(message); }
    public EmptyResponseException(String message, Throwable cause) { super(message, cause); }
    public EmptyResponseException(Throwable cause) { super(cause); }
}
