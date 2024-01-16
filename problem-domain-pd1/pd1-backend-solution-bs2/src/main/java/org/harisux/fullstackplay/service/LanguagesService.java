package org.harisux.fullstackplay.service;

import org.openapi.quarkus.sakila_films_crud_yml.model.LanguageList;

public interface LanguagesService {
    
    public LanguageList getLanguages() throws Exception;

}
