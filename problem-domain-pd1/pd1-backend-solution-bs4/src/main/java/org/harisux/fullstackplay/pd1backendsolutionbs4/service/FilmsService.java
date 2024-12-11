package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;

import reactor.core.publisher.Mono;

public interface FilmsService {
    
    public Mono<FilmList> getFilmList(Integer limit, 
                    Integer offset, String sortBy, String order);

    public Mono<Film> getFilm(Integer id);

    public Mono<Film> createFilm(Film film);

    public Mono<Void> deleteFilm(Integer id);

    public Mono<Film> updateFilm(Film film);

}
