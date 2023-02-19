package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao;

import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<FilmDto, Integer> {
    
}
