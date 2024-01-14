package org.harisux.fullstackplay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.harisux.fullstackplay.exception.FilmNotFoundException;
import org.jboss.logging.Logger;
import org.openapi.quarkus.sakila_films_crud_yml.model.Film;
import org.openapi.quarkus.sakila_films_crud_yml.model.Language;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FilmsServiceImpl implements FilmsService {

    private static final Logger LOG = Logger.getLogger(FilmsServiceImpl.class);

    @Inject
    AgroalDataSource dataSource;

    @Override
    public Film getFilm(Integer id) throws Exception {
        Film film;
        String query = """
            select 
                F.film_id, F.title, F.description, F.release_year, L.language_id,
                L.name, F.rental_rate, F.length, F.replacement_cost, F.rating, 
                F.special_features
            from film F
            left join language L on F.language_id = L.language_id 
            where film_id = ?
        """;
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setInt(1, id);
            try (
                ResultSet rs = pstmt.executeQuery();
            ) {
                if (rs.next()) {
                    film = new Film();
                    film.setFilmId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));

                    Language lang = new Language();
                    lang.setLanguageId(rs.getInt("language_id"));
                    lang.setName(rs.getString("name"));
                    film.setLanguage(lang);

                    film.setRentalRate(rs.getFloat("rental_rate"));
                    film.setLength(rs.getBigDecimal("length"));
                    film.setReplacementCost(rs.getFloat("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                } else {
                    String errMsg = String.format("Film not found <id=%s>", id);
                    throw new FilmNotFoundException(errMsg);
                }
            }
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to get film");
            throw exp;
        }
        return film;
    }
    
}
