package org.harisux.fullstackplay.pd1_backend_solution_bs4.dao;

import org.harisux.fullstackplay.pd1_backend_solution_bs4.entity.FilmDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FilmsRepository 
        extends ReactiveCrudRepository<FilmDto, Integer> {
    
}
