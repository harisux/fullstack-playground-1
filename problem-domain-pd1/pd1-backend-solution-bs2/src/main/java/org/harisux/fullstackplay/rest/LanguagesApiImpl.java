package org.harisux.fullstackplay.rest;

import org.apache.http.HttpStatus;
import org.harisux.fullstackplay.exception.ResponseExceptionHandler;
import org.harisux.fullstackplay.service.LanguagesService;
import org.openapi.quarkus.sakila_films_crud_yml.api.LanguagesApi;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class LanguagesApiImpl implements LanguagesApi {

    @Inject
    LanguagesService languagesService;

    @Inject
    ResponseExceptionHandler exceptionHandler;

    @Override
    public Response getLanguages() {
        return exceptionHandler.handle(() -> {
            return Response.status(HttpStatus.SC_OK)
                .entity(languagesService.getLanguages()).build();
        });
    }
    
}
