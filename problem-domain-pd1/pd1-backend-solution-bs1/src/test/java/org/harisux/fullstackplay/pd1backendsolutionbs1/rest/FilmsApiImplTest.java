package org.harisux.fullstackplay.pd1backendsolutionbs1.rest;

import org.harisux.fullstackplay.openapi.model.Film;
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


}

