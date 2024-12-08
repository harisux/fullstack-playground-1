package org.harisux.fullstackplay.pd1backendsolutionbs4.exception;

import org.harisux.fullstackplay.pd1backendsolutionbs4.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class FilmsExceptionHandler extends ResponseStatusExceptionHandler {
    
    @ExceptionHandler(FilmNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(FilmNotFoundException exception) {
        log.error("Film not found error!", exception);
        return Mono.just(
            new ResponseEntity<>(
                new ErrorResponse("ERR001", exception.getMessage()), 
                HttpStatus.NOT_FOUND)
        );
    }

    @ExceptionHandler(FilmListMissingParamsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(FilmListMissingParamsException exception) {
        log.error("Missing parameter!", exception);
        return Mono.just(
            new ResponseEntity<>(
                new ErrorResponse("ERR002", exception.getMessage()), 
                HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(Exception exception) {
        log.error("Default error!", exception);
        return Mono.just(
            new ResponseEntity<ErrorResponse>(
                new ErrorResponse("ERR000", "Something went wrong..."),
                HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

}
