package org.harisux.fullstackplay.service;

import org.openapi.quarkus.sakila_films_crud_yml.model.Film;
import org.openapi.quarkus.sakila_films_crud_yml.model.FilmList;

public interface FilmsService {
    
    public Film getFilm(Integer id) throws Exception;

    public FilmList getFilmList(Integer limit, 
            Integer offset, String sortBy, String order) throws Exception;

    public Film createFilm(Film film) throws Exception;
    
}
