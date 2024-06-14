package com.example.book.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> noRes(NoResourceFoundException ex) {
        logger.info(ex.getMessage());
        return new ResponseEntity<>("Resource not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> entityNotFound(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Invalid id in request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> invalidNameFormat(InvalidNameFormatException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Name should be in the format of [firstName]+[lastName]",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> mismatchType(MethodArgumentTypeMismatchException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Id should be numeric", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> invalidHttpMethod(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> invalidUrl(org.springframework.web.servlet.resource.NoResourceFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Invalid path", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> invalidRequestBody(NullPointerException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Invalid request body format", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> generalExceptions(Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>("An error occurred at server side", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
