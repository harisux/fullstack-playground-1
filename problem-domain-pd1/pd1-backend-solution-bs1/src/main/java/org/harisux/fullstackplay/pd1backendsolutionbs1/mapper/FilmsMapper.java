package org.harisux.fullstackplay.pd1backendsolutionbs1.mapper;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmsMapper {
    
    @Mapping(target = "releaseYear", expression = "java(filmDto.getReleaseYear().getYear())")
    @Mapping(target = "specialFeatures", expression = "java(String.join(\",\", filmDto.getSpecialFeatures()))")
    @Mapping(target = "languageId", expression = "java(filmDto.getLanguage().getLanguageId())")
    @Mapping(target = "originalLanguageId", expression = "java(filmDto.getOriginalLanguage().getLanguageId())")
    Film filmDtoToFilm(FilmDto filmDto);

}