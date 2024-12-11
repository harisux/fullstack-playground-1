package org.harisux.fullstackplay.pd1backendsolutionbs4.rest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.harisux.fullstackplay.openapi.api.FilmsApi;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs4.service.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/v1")
public class FilmsApiImpl implements FilmsApi {

    @Autowired
    FilmsService filmsService;

    @Override
    public Mono<ResponseEntity<FilmList>> getFilmList(@NotNull @Min(1) @Max(100) @Valid Integer limit,
            @Min(0) @Valid Integer offset, @Valid String sortBy, @Valid String order, ServerWebExchange exchange) {
        return filmsService.getFilmList(limit, offset, sortBy, order).map(filmList -> ResponseEntity.ok(filmList)); 
    }


    @Override
    public Mono<ResponseEntity<Film>> getFilm(Integer id, ServerWebExchange exchange) {
        return filmsService.getFilm(id).map(film -> ResponseEntity.ok(film));
    }


    @Override
    public Mono<ResponseEntity<Film>> createFilm(@Valid Mono<Film> film, ServerWebExchange exchange) {
        // Generator creating input parameters wrapped in Mono?? 
        return film.flatMap(f1 -> 
                filmsService.createFilm(f1).map(f2 -> ResponseEntity.ok(f2)));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteFilm(Integer id, ServerWebExchange exchange) {
        return filmsService.deleteFilm(id).map(o -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
    }

    @Override
    public Mono<ResponseEntity<Film>> updateFilm(@Valid Mono<Film> film, ServerWebExchange exchange) {
        return film.flatMap(f1 -> 
                filmsService.updateFilm(f1).map(f2 -> ResponseEntity.ok(f2)));
    }
    
}
