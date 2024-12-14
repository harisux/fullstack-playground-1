package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import org.harisux.fullstackplay.openapi.model.LanguageList;

import reactor.core.publisher.Mono;

public interface LanguagesService {
    
    public Mono<LanguageList> getLanguages();

}
