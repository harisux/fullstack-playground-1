package org.harisux.fullstackplay.pd1_backend_solution_bs4.rest;

import org.harisux.fullstackplay.openapi.api.FilmsApi;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.pd1_backend_solution_bs4.service.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Mono<Film> getFilm(Integer id, ServerWebExchange exchange) {
        return filmsService.getFilm(id);
    }

  
    
}
