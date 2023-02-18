package org.harisux.fullstackplay.pd1backendsolutionbs1.exception;

import org.harisux.fullstackplay.pd1backendsolutionbs1.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FilmsExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(FilmNotFoundException exception) {
        return new ResponseEntity<>(
            new ErrorResponse("ERR001", exception.getMessage()), 
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return new ResponseEntity<ErrorResponse>(
            new ErrorResponse("ERR000", "Something went wrong..."),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
