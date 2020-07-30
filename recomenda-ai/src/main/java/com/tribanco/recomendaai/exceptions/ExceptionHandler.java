package com.tribanco.recomendaai.exceptions;

import com.tribanco.recomendaai.constants.ErrorMessages;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, ErrorMessages.ERRO_CAMPO_NAO_INFORMADO.getErro(), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorMessages.ERRO_CAMPO_NAO_EXISTE.getErro(), headers, HttpStatus.BAD_REQUEST, request);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({ HttpServerErrorException.class })
    public ResponseEntity<Object> handleHttpServerErrorException (HttpServerErrorException ex, WebRequest request) {
        return  handleExceptionInternal(ex, ErrorMessages.ERRO_SERVER.getErro(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ InvalidDataAccessResourceUsageException.class })
    public ResponseEntity<Object> handleInvalidDataAccessResourceUsageException (InvalidDataAccessResourceUsageException ex, WebRequest request) {
        return  handleExceptionInternal(ex, ErrorMessages.ERRO_SERVER.getErro(),  new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
