package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import java.util.List;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.exception.FilmNotFoundException;
import org.harisux.fullstackplay.pd1backendsolutionbs1.mapper.FilmsMapper;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.FilmsRepository;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    FilmsRepository filmsRepository;

    @Autowired
    FilmsMapper filmsMapper;

    @Override
    @Transactional
    public Film createFilm(Film film) {
        return null;
        // return filmsRepository.save(film);
    }

    @Override
    @Transactional
    public FilmList getFilmList() {
        FilmList filmList = new FilmList();
        List<FilmDto> dbFilms = filmsRepository.findAll();
        filmList.setData(filmsMapper.FilmDtoListToFilmList(dbFilms));
        return filmList;
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        return null;
        // return filmsRepository.save(film);
    }

    @Override
    @Transactional
    public void deleteFilm(Integer id) {
        filmsRepository.findById(id).ifPresentOrElse(
            f -> filmsRepository.delete(f), 
            () -> { 
                String message = String.format("Film with id=%d not found!", id);
                throw new FilmNotFoundException(message); 
            }
        );
    }

    @Override
    @Transactional
    public Film getFilm(Integer id) {
        return null;
        /*return filmsRepository.findById(id)
            .orElseThrow(() -> {
                String message = String.format("Film with id=%d not found!", id); 
                return new FilmNotFoundException(message);
            });*/
    }
    
}
