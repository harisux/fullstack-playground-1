package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.pd1backendsolutionbs1.service.FilmsService;
import org.harisux.fullstackplay.pd1backendsolutionbs1.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FilmsApiImpl.class, 
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class FilmsApiImplTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FilmsService filmsService;

    @Test
    public void shouldGetFilm() throws Exception {
        //Given
        Film filmSample = TestUtils.getJsonSample("film-sample-1.json", Film.class);
        Mockito.when(filmsService.getFilm(filmSample.getFilmId())).thenReturn(filmSample);

        //When
        mockMvc.perform(get("/api/v1/films/" + filmSample.getFilmId())
            .contentType(MediaType.APPLICATION_JSON)
        )
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.film_id").value(filmSample.getFilmId()))
        .andExpect(jsonPath("$.title").value(filmSample.getTitle()))
        ;
    }

    @Test
    public void shouldGetFilmList() throws Exception {
        //Given
        FilmList filmListSample = TestUtils.getJsonSample("film-list-sample-1.json", FilmList.class);
        Mockito.when(filmsService.getFilmList(2, 2, "film_id", "asc"))
        .thenReturn(filmListSample);

        //When
        mockMvc.perform(get("/api/v1/films")
            .queryParam("limit", "2")
            .queryParam("offset", "2")
            .queryParam("sort_by", "film_id")
            .queryParam("order", "asc")
            .accept(MediaType.APPLICATION_JSON)
        )
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(hasSize(filmListSample.getData().size())))
        .andExpect(jsonPath("$.data[0].film_id").value(filmListSample.getData().get(0).getFilmId()))
        .andExpect(jsonPath("$.data[0].title").value(filmListSample.getData().get(0).getTitle()))
        ;
    }

    @Test
    public void shouldDeleteFilm() throws Exception {
        //Given
        int testId = 123;
        Mockito.doNothing().when(filmsService).deleteFilm(testId);

        //When
        mockMvc.perform(delete("/api/v1/films/" + testId))
        //Then
        .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateFilm() throws Exception {
        //Given
        Film filmSample = TestUtils.getJsonSample("film-sample-1.json", Film.class);
        String filmSampleStr = TestUtils.getJsonSampleAsText("film-sample-1.json");
        
        int createdId = 1234;
        Mockito.when(filmsService.createFilm(Mockito.any(Film.class)))
            .thenAnswer(i -> {
                Film filmIn = (Film) i.getArguments()[0];
                filmIn.setFilmId(createdId);
                return filmIn;
            });

        //When
        mockMvc.perform(post("/api/v1/films")
            .contentType(MediaType.APPLICATION_JSON)
            .content(filmSampleStr)
            .accept(MediaType.APPLICATION_JSON)
        )
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.film_id").value(createdId))
        .andExpect(jsonPath("$.title").value(filmSample.getTitle()))
        .andExpect(jsonPath("$.description").value(filmSample.getDescription()))
        ;
    }

    @Test
    public void shouldUpdateFilm() throws Exception {
        //Given
        Film filmSample = TestUtils.getJsonSample("film-sample-1.json", Film.class);
        String filmSampleStr = TestUtils.getJsonSampleAsText("film-sample-1.json");
        
        OffsetDateTime updatedTime = OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class)))
            .thenAnswer(i -> {
                Film filmIn = (Film) i.getArguments()[0];
                filmIn.setLastUpdate(updatedTime);
                return filmIn;
            });

        //When
        mockMvc.perform(put("/api/v1/films")
            .contentType(MediaType.APPLICATION_JSON)
            .content(filmSampleStr)
            .accept(MediaType.APPLICATION_JSON)
        )
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.last_update").value(updatedTime.toString()))
        .andExpect(jsonPath("$.film_id").value(filmSample.getFilmId()))
        .andExpect(jsonPath("$.title").value(filmSample.getTitle()))
        ;
    }

}

