package org.harisux.fullstackplay.pd1_backend_solution_bs4.service;

import org.harisux.fullstackplay.openapi.model.Film;

import reactor.core.publisher.Mono;

public interface FilmsService {
    
    public Mono<Film> getFilm(Integer id);

}
