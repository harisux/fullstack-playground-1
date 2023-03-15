package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import org.harisux.fullstackplay.openapi.model.LanguageList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.mapper.LanguagesMapper;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.LanguagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LanguagesServiceImpl implements LanguagesService {

    @Autowired
    LanguagesRepository langRepository;

    @Autowired
    LanguagesMapper langMapper;

    @Override
    @Transactional
    public LanguageList getLanguages() {
        LanguageList langList = new LanguageList();
        langList.setData(
            langMapper.languageDtoListToLanguageList(
                langRepository.findAll()
            )
        );
        return langList;
    }
    
}
