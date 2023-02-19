package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.exception.FilmNotFoundException;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    FilmRepository filmRepository;

    @Override
    public Film createFilm(Film film) {
        return null;
        // return filmRepository.save(film);
    }

    @Override
    public FilmList getFilmList() {
        FilmList filmList = new FilmList();
        //filmList.setData(filmRepository.findAll());
        return filmList;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
        // return filmRepository.save(film);
    }

    @Override
    public void deleteFilm(Integer id) {
        filmRepository.findById(id).ifPresentOrElse(
            f -> filmRepository.delete(f), 
            () -> { 
                String message = String.format("Film with id=%d not found!", id);
                throw new FilmNotFoundException(message); 
            }
        );
    }

    @Override
    public Film getFilm(Integer id) {
        return null;
        /*return filmRepository.findById(id)
            .orElseThrow(() -> {
                String message = String.format("Film with id=%d not found!", id); 
                return new FilmNotFoundException(message);
            });*/
    }
    
}
