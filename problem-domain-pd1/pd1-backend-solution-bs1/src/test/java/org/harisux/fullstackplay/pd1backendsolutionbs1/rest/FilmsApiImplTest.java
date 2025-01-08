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

        //When + Then
        mockMvc.perform(get("/api/v1/films/" + filmSample.getFilmId())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("film_id").value(filmSample.getFilmId()))
        .andExpect(jsonPath("title").value(filmSample.getTitle()))
        ;
    }

    @Test
    public void shouldGetFilmList() throws Exception {
        //Given
        FilmList filmListSample = TestUtils.getJsonSample("film-list-sample-1.json", FilmList.class);
        Mockito.when(filmsService.getFilmList(2, 2, "film_id", "asc"))
        .thenReturn(filmListSample);

        //When + Then
        mockMvc.perform(get("/api/v1/films")
            .queryParam("limit", "2")
            .queryParam("offset", "2")
            .queryParam("sort_by", "film_id")
            .queryParam("order", "asc")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("data").value(hasSize(filmListSample.getData().size())))
        .andExpect(jsonPath("data[0].film_id").value(filmListSample.getData().get(0).getFilmId()))
        .andExpect(jsonPath("data[0].title").value(filmListSample.getData().get(0).getTitle()))
        ;
    }


}

