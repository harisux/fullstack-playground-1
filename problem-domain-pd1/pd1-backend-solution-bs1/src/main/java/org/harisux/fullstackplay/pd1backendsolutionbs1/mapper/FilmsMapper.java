package org.harisux.fullstackplay.pd1backendsolutionbs1.mapper;

import java.util.List;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmsMapper {
    
    @Mapping(target = "releaseYear", expression = "java(filmDto.getReleaseYear().getYear())")
    Film filmDtoToFilm(FilmDto filmDto);

    List<Film> FilmDtoListToFilmList(List<FilmDto> filmDtoArr);
}
