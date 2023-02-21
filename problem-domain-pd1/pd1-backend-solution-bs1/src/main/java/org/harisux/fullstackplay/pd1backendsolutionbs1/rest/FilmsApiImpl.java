package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.harisux.fullstackplay.openapi.api.FilmsApi;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.service.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(filmsService.createFilm(film));
    }

    @Override
    public ResponseEntity<FilmList> getFilmList(@NotNull @Min(1) @Max(100) @Valid Integer limit,
            @Min(0) @Valid Integer offset, @Valid String sortBy, @Valid String order) {
        return ResponseEntity.ok(filmsService.getFilmList(limit, offset, sortBy, order));
    }

    @Override
    public ResponseEntity<Film> updateFilm(@Valid Film film) {
        return ResponseEntity.ok(filmsService.updateFilm(film));
    }

    @Override
    public ResponseEntity<Void> deleteFilm(Integer id) {
        filmsService.deleteFilm(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Film> getFilm(Integer id) {
        return ResponseEntity.ok(filmsService.getFilm(id));
    }

}
