package org.harisux.fullstackplay.pd1backendsolutionbs4.rest;

import org.harisux.fullstackplay.openapi.api.LanguagesApi;
import org.harisux.fullstackplay.openapi.model.LanguageList;
import org.harisux.fullstackplay.pd1backendsolutionbs4.service.LanguagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/v1")
public class LanguagesApiImpl implements LanguagesApi {

    @Autowired
    LanguagesService languagesService;

    @Override
    public Mono<ResponseEntity<LanguageList>> getLanguages(ServerWebExchange exchange) {
        return languagesService.getLanguages().map(langList -> ResponseEntity.ok(langList));
    }
    
}
