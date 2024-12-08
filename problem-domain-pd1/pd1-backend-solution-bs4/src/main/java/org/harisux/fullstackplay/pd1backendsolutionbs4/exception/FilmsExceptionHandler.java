package org.harisux.fullstackplay.pd1backendsolutionbs4.exception;

import org.harisux.fullstackplay.pd1backendsolutionbs4.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class FilmsExceptionHandler extends ResponseStatusExceptionHandler {
    
    @ExceptionHandler(FilmNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(FilmNotFoundException exception) {
        return Mono.just(
            new ResponseEntity<>(
                new ErrorResponse("ERR001", exception.getMessage()), 
                HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(Exception exception) {
        return Mono.just(
            new ResponseEntity<ErrorResponse>(
                new ErrorResponse("ERR000", "Something went wrong..."),
                HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

}
