package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao;

import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    
}
