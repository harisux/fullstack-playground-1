package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;

public interface FilmsService {
    
    public Film createFilm(Film film);
    
    public FilmList getFilmList();

    public Film updateFilm(Film film);

    public void deleteFilm(Integer id);

    public Film getFilm(Integer id);

}
