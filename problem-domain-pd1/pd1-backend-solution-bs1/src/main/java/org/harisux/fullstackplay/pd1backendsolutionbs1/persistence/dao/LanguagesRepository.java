package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao;

import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.LanguageDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository 
            extends JpaRepository<LanguageDto, Integer> {
    
}
