package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.text.CaseUtils;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.exception.FilmNotFoundException;
import org.harisux.fullstackplay.pd1backendsolutionbs1.mapper.FilmsMapper;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.FilmsRepository;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.OffsetLimitSortPageReq;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    FilmsRepository filmsRepository;

    @Autowired
    FilmsMapper filmsMapper;

    @Override
    @Transactional
    public Film createFilm(Film film) {
        FilmDto filmDto = filmsMapper.filmToFilmDto(film);
        FilmDto savedFilmDto = filmsRepository.save(filmDto);
        return filmsMapper.filmDtoToFilm(savedFilmDto);
    }

    @Override
    @Transactional
    public FilmList getFilmList(Integer limit, Integer offset, String sortBy, String order) {
        FilmList filmList = new FilmList();
        Pageable pageableReq = this.assemblePageableRequest(limit, offset, sortBy, order);
        List<FilmDto> dbFilms = filmsRepository.findAll(pageableReq).toList();
        filmList.setData(filmsMapper.FilmDtoListToFilmList(dbFilms));
        filmList.setTotalCount(new BigDecimal(filmsRepository.count()));
        return filmList;
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        FilmDto filmDto = filmsMapper.filmToFilmDto(film);
        FilmDto savedFilmDto = filmsRepository.save(filmDto);
        return filmsMapper.filmDtoToFilm(savedFilmDto);
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
        FilmDto dbFilm = filmsRepository.findById(id)
            .orElseThrow(() -> {
                String message = String.format("Film with id=%d not found!", id); 
                return new FilmNotFoundException(message);
        });
        return filmsMapper.filmDtoToFilm(dbFilm);
    }

    private Pageable assemblePageableRequest(Integer limit, Integer offset, String sortBy, String order) {
        sortBy = CaseUtils.toCamelCase(sortBy, false, '_');
        order = order.toUpperCase();
        Sort sortObj = Sort.by(Sort.Direction.valueOf(order), sortBy);
        OffsetLimitSortPageReq pageableReq = new OffsetLimitSortPageReq(offset, limit, sortObj);
        return pageableReq;
    }
    
}
