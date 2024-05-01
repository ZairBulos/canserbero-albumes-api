package com.zair.controllers;

import com.zair.exceptions.AlbumNotFoundException;
import com.zair.exceptions.InvalidAlbumIdException;
import com.zair.models.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAlbumIdException.class)
    public ResponseEntity<ErrorMessage> handlerInvalidAlbumIdException(InvalidAlbumIdException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerAlbumNotFoundException(AlbumNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handlerException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
