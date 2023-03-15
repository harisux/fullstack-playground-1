package org.harisux.fullstackplay.pd1backendsolutionbs1.mapper;

import java.util.List;

import org.harisux.fullstackplay.openapi.model.Language;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.LanguageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguagesMapper {
    
    Language languageDtoToLanguage(LanguageDto langDto);

    List<Language> languageDtoListToLanguageList(
                        List<LanguageDto> langDtoArr);
    
}
