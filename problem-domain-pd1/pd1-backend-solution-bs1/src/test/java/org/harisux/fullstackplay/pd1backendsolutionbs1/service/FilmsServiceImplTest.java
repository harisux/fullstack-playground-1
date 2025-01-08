package org.harisux.fullstackplay.pd1backendsolutionbs1.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.exception.FilmNotFoundException;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Test
    public void shouldThrowExceptionWhenFilmIdNotFoundOnGet() {
        //Given
        Mockito.when(filmsRepositoryMock.findById(Mockito.anyInt()))
            .thenReturn(Optional.empty());
        
        //When + Then
        assertThatExceptionOfType(FilmNotFoundException.class)
            .isThrownBy(() -> {
                filmsServiceImpl.getFilm(100);
            })
            .withMessageContaining("Film with id=100 not found!")
        ;
    }

    @Test
    public void shouldGetFilmList() {
        //Given
        List<FilmDto> filmDtoListSample = Arrays.asList(
            TestUtils.getJsonSample("filmdto-list-sample-1.json", 
            FilmDto[].class));

        Mockito.when(filmsRepositoryMock.findAll(Mockito.any(Pageable.class)))
            .thenReturn(new PageImpl<>(filmDtoListSample));
        Mockito.when(filmsRepositoryMock.count()).thenReturn(Long.valueOf(100));
        
        //When
        FilmList filmList 
            = filmsServiceImpl.getFilmList(2, 5, "filmId", "asc");

        //Then
        assertThat(filmList).isNotNull();
        assertThat(filmList.getTotalCount()).isEqualTo(new BigDecimal(100));
        assertThat(filmList.getData()).isNotNull().hasSize(2);
        
        assertThat(filmList.getData().get(0))
            .hasFieldOrPropertyWithValue("filmId", filmDtoListSample.get(0).getFilmId())
            .hasFieldOrPropertyWithValue("title", filmDtoListSample.get(0).getTitle());
        assertThat(filmList.getData().get(1))
            .hasFieldOrPropertyWithValue("filmId", filmDtoListSample.get(1).getFilmId())
            .hasFieldOrPropertyWithValue("description", filmDtoListSample.get(1).getDescription());
    }

    @Test
    public void shouldDeleteFilm() {
        //Given
        Mockito.when(filmsRepositoryMock.findById(Mockito.anyInt()))
            .thenReturn(Optional.of(new FilmDto()));

        Mockito.doNothing().when(filmsRepositoryMock).delete(Mockito.any(FilmDto.class));
        
        //When + Then
        assertThatNoException().isThrownBy(() -> filmsServiceImpl.deleteFilm(1));
    }

    @Test
    public void shouldThrowExceptionWhenFilmIdNotFoundOnDelete() {
        //Given
        Mockito.when(filmsRepositoryMock.findById(Mockito.anyInt()))
            .thenReturn(Optional.empty());

        Mockito.doNothing().when(filmsRepositoryMock).delete(Mockito.any(FilmDto.class));
        
        //When + Then
        assertThatExceptionOfType(FilmNotFoundException.class)
            .isThrownBy(() -> {
                filmsServiceImpl.deleteFilm(100);
            })
            .withMessageContaining("Film with id=100 not found!")
        ;
    }

    @Test
    public void shouldCreateFilm() {
        //Given
        Film filmSample = TestUtils.getJsonSample("film-sample-1.json", Film.class);

        Integer createdId = 1234;
        Mockito.when(filmsRepositoryMock.save(Mockito.any(FilmDto.class)))
            .thenAnswer(i -> {
                FilmDto filmDtoIn = (FilmDto) i.getArguments()[0];
                filmDtoIn.setFilmId(createdId);
                return filmDtoIn;
            });

        //When
        Film createdFilm = filmsServiceImpl.createFilm(filmSample);

        //Then
        assertThat(createdFilm)
            .isNotNull()
            .hasFieldOrPropertyWithValue("filmId", createdId)
            .hasFieldOrPropertyWithValue("title", filmSample.getTitle())
            .hasFieldOrPropertyWithValue("description", filmSample.getDescription())
        ;
    }

}
