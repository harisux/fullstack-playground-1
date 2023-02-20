package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import javax.validation.Valid;

import org.harisux.fullstackplay.openapi.api.FilmsApi;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.service.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
public class FilmsApiImpl implements FilmsApi {

    @Autowired
    FilmsService filmsService;

    @Override
    public ResponseEntity<Film> createFilm(@Valid Film film) {
        return null;
    }

    @Override
    public ResponseEntity<FilmList> getFilmList() {
        return ResponseEntity.ok(filmsService.getFilmList());
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
