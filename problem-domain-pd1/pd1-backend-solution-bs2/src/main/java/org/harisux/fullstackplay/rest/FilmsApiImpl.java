package org.harisux.fullstackplay.rest;

import org.apache.http.HttpStatus;
import org.harisux.fullstackplay.exception.ResponseExceptionHandler;
import org.harisux.fullstackplay.service.FilmsService;
import org.openapi.quarkus.sakila_films_crud_yml.api.FilmsApi;
import org.openapi.quarkus.sakila_films_crud_yml.model.Film;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class FilmsApiImpl implements FilmsApi {

    @Inject
    FilmsService filmsService;

    @Inject
    ResponseExceptionHandler exceptionHandler;

    @Override
    public Response createFilm(Film film) {
        return exceptionHandler.handle(() -> {
            return Response.status(HttpStatus.SC_OK)
                .entity(filmsService.createFilm(film)).build();
        });
    }

    @Override
    public Response deleteFilm(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFilm'");
    }

    @Override
    public Response getFilm(Integer id) {
        return exceptionHandler.handle(() -> {
            return Response.status(HttpStatus.SC_OK)
                .entity(filmsService.getFilm(id)).build();
        });
    }

    @Override
    public Response getFilmList(Integer limit, Integer offset, String sortBy, String order) {
        return exceptionHandler.handle(() -> {
            return Response.status(HttpStatus.SC_OK)
                .entity(filmsService.getFilmList(limit, offset, sortBy, order)).build();
        });
    }

    @Override
    public Response updateFilm(Film film) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFilm'");
    }
    
}
