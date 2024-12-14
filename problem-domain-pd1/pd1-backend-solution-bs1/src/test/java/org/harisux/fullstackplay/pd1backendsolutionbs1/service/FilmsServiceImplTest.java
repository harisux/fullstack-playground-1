package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.pd1backendsolutionbs1.mapper.FilmsMapper;
import org.harisux.fullstackplay.pd1backendsolutionbs1.mapper.FilmsMapperImpl;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao.FilmsRepository;
import org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity.FilmDto;
import org.harisux.fullstackplay.pd1backendsolutionbs1.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = { FilmsServiceImpl.class, FilmsMapperImpl.class })
public class FilmsServiceImplTest {

    @MockBean
    FilmsRepository filmsRepositoryMock;

    @Autowired
    FilmsMapper filmsMapper;

    @Autowired
    FilmsServiceImpl filmsServiceImpl;
    
    @Test
    public void shouldGetFilmById() {
        //Given
        FilmDto filmDtoSample = TestUtils.getJsonSample("filmdto-sample-1.json", FilmDto.class);

        Mockito.when(filmsRepositoryMock.findById(Mockito.anyInt()))
            .thenReturn(Optional.of(filmDtoSample));
        
        //When
        Film film = filmsServiceImpl.getFilm(1);

        //Then
        assertThat(film)
            .isNotNull()
            .hasFieldOrPropertyWithValue("filmId", filmDtoSample.getFilmId())
            .hasFieldOrPropertyWithValue("title", filmDtoSample.getTitle())
            .hasFieldOrPropertyWithValue("description", filmDtoSample.getDescription())
            .hasFieldOrPropertyWithValue("length", new BigDecimal(filmDtoSample.getLength()))
            .hasFieldOrPropertyWithValue("releaseYear", filmDtoSample.getReleaseYear())
        ;
    }
    
}
