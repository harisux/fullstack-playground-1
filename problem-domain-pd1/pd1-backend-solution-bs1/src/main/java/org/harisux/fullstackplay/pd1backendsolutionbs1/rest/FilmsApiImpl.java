package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import javax.validation.Valid;

import org.harisux.fullstackplay.openapi.api.FilmsApi;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.springframework.http.ResponseEntity;

public class FilmsApiImpl implements FilmsApi {

    @Override
    public ResponseEntity<Film> createFilm(@Valid Film film) {
        return null;
    }

    @Override
    public ResponseEntity<FilmList> getFilmList() {
        return null;
    }

    @Override
    public ResponseEntity<Film> updateFilm(@Valid Film film) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteFilm(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Film> getFilm(Integer id) {
        return null;
    }
    
}
