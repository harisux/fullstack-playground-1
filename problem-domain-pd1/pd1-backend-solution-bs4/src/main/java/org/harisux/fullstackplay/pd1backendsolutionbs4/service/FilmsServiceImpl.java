package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import java.math.BigDecimal;
import java.util.Map;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.Language;
import org.harisux.fullstackplay.pd1backendsolutionbs4.exception.FilmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    DatabaseClient databaseClient;

    @Override
    public Mono<Film> getFilm(Integer id) {
        
        String query = """
            select 
                F.film_id, F.title, F.description, F.release_year, F.language_id,
                L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
                F.rating, F.special_features
            from film F
            left join language L on F.language_id = L.language_id 
            where film_id = :filmId
        """;

        return databaseClient.sql(query).bind("filmId", id)
            .fetch().first()
            .switchIfEmpty(Mono.error(
                new FilmNotFoundException(
                    String.format("Film not found <id=%s>", id))
            ))
            .map(row -> populateFilmFromRow(row))
        ;
    }

    private Film populateFilmFromRow(Map<String, Object> row) {
        Film film = new Film();
        film.setFilmId((Integer) row.get("film_id"));
        film.setTitle((String) row.get("title"));
        film.setDescription((String) row.get("description"));
        film.setReleaseYear(((Short) row.get("release_year")).intValue());

        Language lang = new Language();
        lang.setLanguageId(((Short) row.get("language_id")).intValue());
        lang.setName((String) row.get("name"));
        film.setLanguage(lang);

        film.setRentalDuration(BigDecimal.valueOf((Short) row.get("rental_duration")));
        film.setRentalRate(((BigDecimal) row.get("rental_rate")).floatValue());
        film.setLength(BigDecimal.valueOf((Integer) row.get("length")));
        film.setReplacementCost(((BigDecimal) row.get("replacement_cost")).floatValue());
        film.setRating((String) row.get("rating"));
        film.setSpecialFeatures(String.join(",", (String[]) row.get("special_features")));
        return film;
    }
    
}
