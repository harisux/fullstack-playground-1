package org.harisux.fullstackplay.service;

import org.openapi.quarkus.sakila_films_crud_yml.model.Film;

public interface FilmsService {
    
    public Film getFilm(Integer id) throws Exception;

}
