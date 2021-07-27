package com.training.blinkist.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

    @EnableWebMvc
    @ControllerAdvice
    public class BlinkistExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler({ BookNotPresentException.class, BookDoesNotExistInUserLibraryException.class})
        public ResponseEntity<ErrorResponse> handleCustomNotFoundException(Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, Arrays.asList(exception.getMessage()), new Date()), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler({BookAlreadyPresentException.class, BookAlreadyExisitsInUserLibrary.class,
                 UserAlreadyPresentException.class})
        public ResponseEntity<ErrorResponse> handleCustomAlreadyExistsException(Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT, Arrays.asList(exception.getMessage()), new Date()), HttpStatus.CONFLICT);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            var details = new ArrayList<String>();
            for (ObjectError error : ex.getBindingResult().getAllErrors()) {
                details.add(error.getDefaultMessage());
            }
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, details, new Date()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({Exception.class})
        public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, Arrays.asList(exception.getMessage()), new Date()), HttpStatus.BAD_REQUEST);
        }
    }

