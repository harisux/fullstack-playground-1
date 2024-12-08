package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;

import reactor.core.publisher.Mono;

public interface FilmsService {
    
    public Mono<FilmList> getFilmList(Integer limit, 
                    Integer offset, String sortBy, String order);

    public Mono<Film> getFilm(Integer id);

}
