package org.harisux.fullstackplay.exception;

public class FilmNotFoundException extends RuntimeException {
    
    public FilmNotFoundException(String message) {
        super(message);
    }

}
