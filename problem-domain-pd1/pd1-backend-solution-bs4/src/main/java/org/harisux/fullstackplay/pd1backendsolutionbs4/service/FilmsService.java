package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import org.harisux.fullstackplay.openapi.model.Film;

import reactor.core.publisher.Mono;

public interface FilmsService {
    
    public Mono<Film> getFilm(Integer id);

}
