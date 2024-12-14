package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import java.time.ZoneOffset;
import java.util.ArrayList;

import org.harisux.fullstackplay.openapi.model.Language;
import org.harisux.fullstackplay.openapi.model.LanguageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LanguagesServiceImpl implements LanguagesService {

    @Autowired
    DatabaseClient databaseClient;

    @Override
    public Mono<LanguageList> getLanguages() {  
        return databaseClient.sql("select language_id, name from language")
                    .fetch().all()
                    .collectList()
                    .map(rows -> {
                        LanguageList languageList = new LanguageList();
                        languageList.setData(new ArrayList<>());
                        rows.forEach(row -> {
                            Language language = new Language();
                            language.setLanguageId(((Short) row.get("language_id")).intValue());
                            language.setName((String) row.get("name"));
                            languageList.addDataItem(language);
                        });
                        return languageList;
                    })
        ;
    }
    
}
