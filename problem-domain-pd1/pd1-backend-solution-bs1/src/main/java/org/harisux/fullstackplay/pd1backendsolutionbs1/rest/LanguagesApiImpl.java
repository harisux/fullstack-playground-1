package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import org.harisux.fullstackplay.openapi.api.LanguagesApi;
import org.harisux.fullstackplay.openapi.model.LanguageList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.service.LanguagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LanguagesApiImpl implements LanguagesApi {

    @Autowired
    LanguagesService languagesService;

    @Override
    public ResponseEntity<LanguageList> getLanguages() {
        return ResponseEntity.ok(languagesService.getLanguages());
    }
    
}
