package org.harisux.fullstackplay.pd1backendsolutionbs4.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harisux.fullstackplay.openapi.model.Film;
import org.harisux.fullstackplay.openapi.model.FilmList;
import org.harisux.fullstackplay.openapi.model.Language;
import org.harisux.fullstackplay.pd1backendsolutionbs4.exception.FilmListMissingParamsException;
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

    @Override
    public Mono<FilmList> getFilmList(Integer limit, Integer offset, String sortBy, String order) {
        //Validations
        if (limit == null || limit < 0) {
            return Mono.error(
                new FilmListMissingParamsException("'limit' query parameter is required"));
        }
        
        boolean hasOffset = offset != null && offset > 0;
        String query = """
            select 
                F.film_id, F.title, F.description, F.release_year, F.language_id,
                L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
                F.rating, F.special_features
            from film F
            left join language L on F.language_id = L.language_id 
        """;

        String sortField = this.sanitizeFilmSortField(sortBy);
        query = this.addOrderBy(query, sortField, order);
        query += " limit :limitOffsetVal1" + (hasOffset ? ",:limitOffsetVal2" : "");

        Map<String, Object> bindParams = new HashMap<String, Object>();
        if (hasOffset) {
            bindParams.put("limitOffsetVal1", offset);
            bindParams.put("limitOffsetVal2", limit);
        } else {
            bindParams.put("limitOffsetVal1", limit);
        }

        return databaseClient.sql(query).bindValues(bindParams)
                    .fetch().all()
                    .collectList()
                    .map(rows -> {
                        FilmList filmList = new FilmList();
                        filmList.setData(new ArrayList<>());
                        rows.forEach(row -> {
                            Film film = this.populateFilmFromRow(row);
                            filmList.addDataItem(film);
                        });
                        return filmList;
                    })
                    .flatMap(filmList -> setFilmsTotalCount(filmList))
        ;
    }

    @Override
    public Mono<Film> createFilm(Film film) {
        String query = """
            insert into film ( 
                title, description, release_year, language_id,
                original_language_id, rental_duration, rental_rate, length, 
                replacement_cost, rating, special_features, last_update
            ) values (
                :title, :description, :release_year, :language_id,
                :original_language_id, :rental_duration, :rental_rate, :length, 
                :replacement_cost, :rating, :special_features, :last_update
            )
        """;

        return databaseClient.sql(query)
                .bind("title", film.getTitle())
                .bind("description", film.getDescription())
                .bind("release_year", film.getReleaseYear())
                .bind("language_id", film.getLanguage().getLanguageId())
                .bindNull("original_language_id", Integer.class)
                .bind("rental_duration", film.getRentalDuration())
                .bind("rental_rate", film.getRentalRate())
                .bind("length", film.getLength())
                .bind("replacement_cost", film.getReplacementCost())
                .bind("rating", film.getRating())
                .bind("special_features", film.getSpecialFeatures())
                .bind("last_update", Instant.now())
                .filter(stmt -> stmt.returnGeneratedValues("id"))
                .map(row -> row.get("id", Integer.class))
                .first()
                .map(createdId -> {
                    film.setFilmId(createdId);
                    return film;
                })
        ;
    }

    @Override
    public Mono<Void> deleteFilm(Integer id) {
        return 
            this.getFilm(id)
                .flatMap(filmFound ->
                    databaseClient.sql("delete from film where film_id = :film_id")
                        .bind("film_id", id).fetch().rowsUpdated()
                )
                .flatMap(rowsUpdated -> Mono.empty())
        ;
    }
    
    @Override
    public Mono<Film> updateFilm(Film film) {
        String query = """
            update film set
                title = :title, description = :description, release_year = :release_year, 
                language_id = :language_id, original_language_id = :original_language_id, 
                rental_duration = :rental_duration, rental_rate = :rental_rate, length = :length, 
                replacement_cost = :replacement_cost, rating = :rating, special_features = :special_features, 
                last_update = :last_update
            where film_id = :film_id
        """;
        return 
            this.getFilm(film.getFilmId())
                .flatMap(existingFilm -> 
                    databaseClient.sql(query)
                        .bind("title", film.getTitle())
                        .bind("description", film.getDescription())
                        .bind("release_year", film.getReleaseYear())
                        .bind("language_id", film.getLanguage().getLanguageId())
                        .bindNull("original_language_id", Integer.class)
                        .bind("rental_duration", film.getRentalDuration())
                        .bind("rental_rate", film.getRentalRate())
                        .bind("length", film.getLength())
                        .bind("replacement_cost", film.getReplacementCost())
                        .bind("rating", film.getRating())
                        .bind("special_features", film.getSpecialFeatures())
                        .bind("last_update", Instant.now())
                        .bind("film_id", film.getFilmId())
                        .fetch().rowsUpdated()
                )
                .flatMap(rowsCount -> Mono.just(film))
        ;
    }

    /*** Auxiliary methods ***/

    private Mono<FilmList> setFilmsTotalCount(FilmList filmList) {
        return databaseClient.sql("select count(*) as total_count from film")
            .fetch().first()
            .map(row -> {
                long totalCount = (Long) row.get("total_count");
                filmList.setTotalCount(new BigDecimal(totalCount));
                return filmList;
            });
    }

    private String sanitizeFilmSortField(String sortFieldIn) {
        String sortField = "F.film_id"; //default
        List<String> allowedOptions = List.of(
            "title", "description", "release_year", 
            "language_id", "rental_rate", "length", "replacement_cost", 
            "rating", "special_features");
        
        if (sortFieldIn != null && allowedOptions.contains(sortFieldIn)) {
            sortField = "F." + sortFieldIn;
        }

        return sortField;
    }

    private String addOrderBy(String query, String field, String inOrderBy) {
        String orderByOpt = 
            inOrderBy != null && inOrderBy.toLowerCase().equals("desc") ? 
            "desc" : "asc";
        query += String.format(" order by %s %s", field, orderByOpt);
        return query;
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
