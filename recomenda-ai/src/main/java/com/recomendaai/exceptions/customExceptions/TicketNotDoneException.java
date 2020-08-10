package com.recomendaai.exceptions.customExceptions;

public class TicketNotDoneException extends Exception {
    public TicketNotDoneException() { super(); }
    public TicketNotDoneException(String message) { super(message); }
    public TicketNotDoneException(String message, Throwable cause) { super(message, cause); }
    public TicketNotDoneException(Throwable cause) { super(cause); }
}
