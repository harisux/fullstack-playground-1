package org.harisux.fullstackplay.pd1_backend_solution_bs4.service;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.pd1_backend_solution_bs4.dao.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    FilmsRepository filmsRepository;

    @Override
    public Mono<Film> getFilm(Integer id) {
        return filmsRepository.findById(id)
            .map(filmDto -> {
                Film film = new Film();
                film.setFilmId(filmDto.getFilmId());
                film.setTitle(filmDto.getTitle());
                film.setDescription(filmDto.getDescription());
                return film;
            });
    }
    
}
