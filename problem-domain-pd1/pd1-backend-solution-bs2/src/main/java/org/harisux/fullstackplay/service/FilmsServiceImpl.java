package org.harisux.fullstackplay.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.harisux.fullstackplay.exception.FilmNotFoundException;
import org.harisux.fullstackplay.exception.MissingParameterException;
import org.jboss.logging.Logger;
import org.openapi.quarkus.sakila_films_crud_yml.model.Film;
import org.openapi.quarkus.sakila_films_crud_yml.model.FilmList;
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
                F.film_id, F.title, F.description, F.release_year, F.language_id,
                L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
                F.rating, F.special_features
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
                    film = this.populateFilmFromRs(rs);
                } else {
                    String errMsg = String.format("Film not found <id=%s>", id);
                    throw new FilmNotFoundException(errMsg);
                }
            }
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to fetch film");
            throw exp;
        }
        return film;
    }

    @Override
    public FilmList getFilmList(
        Integer limit, Integer offset, String sortBy, String order) throws Exception {
        
        //Validations
        if (limit == null || limit < 0) {
            throw new MissingParameterException("'limit' query parameter is required");
        }

        FilmList filmList = new FilmList();
        filmList.setData(new ArrayList<>());
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
        query += " limit ?" + (hasOffset ? ",?" : "");

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            if (hasOffset) {
                pstmt.setInt(1, offset);
                pstmt.setInt(2, limit);
            } else {
                pstmt.setInt(1, limit);
            }

            try (
                ResultSet rs = pstmt.executeQuery();
            ) {
                while (rs.next()) {
                    Film film = this.populateFilmFromRs(rs);
                    filmList.addDataItem(film);
                }
            }
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to fetch film list");
            throw exp;
        }

        //Total count
        long totalCount = getFilmsTotalCount();
        filmList.setTotalCount(new BigDecimal(totalCount));

        return filmList;
    }

    @Override
    public Film createFilm(Film film) throws Exception {
        String query = """
            insert into film ( 
                title, description, release_year, language_id,
                original_language_id, rental_duration, rental_rate, length, 
                replacement_cost, rating, special_features, last_update
            ) values (
                ?,?,?,?,?,?,?,?,?,?,?,?
            )
        """;
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, film.getTitle());
            pstmt.setString(2, film.getDescription());
            pstmt.setInt(3, film.getReleaseYear());
            pstmt.setInt(4, film.getLanguage().getLanguageId());
            pstmt.setNull(5, Types.INTEGER); // original lang id as null
            pstmt.setBigDecimal(6, film.getRentalDuration());
            pstmt.setFloat(7, film.getRentalRate());
            pstmt.setBigDecimal(8, film.getLength());
            pstmt.setFloat(9, film.getReplacementCost());
            pstmt.setString(10, film.getRating());
            pstmt.setString(11, film.getSpecialFeatures());
            pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();

            film.setFilmId(getInsertId(conn));
            
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to insert film");
            throw exp;
        }
        return film;
    }

    @Override
    public void deleteFilm(Integer id) throws Exception {
        //Validation to check if exists (would throw error otherwise)
        Film film = this.getFilm(id); 

        String query = "delete from film where film_id = ?";
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setInt(1, film.getFilmId());
            pstmt.executeUpdate();
            
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to delete film");
            throw exp;
        }
    }

    @Override
    public Film updateFilm(Film film) throws Exception {
        //Validation to check if exists (would throw error otherwise)
        Film existsFilm = this.getFilm(film.getFilmId()); 

        String query = """
            update film set
                title = ?, description = ?, release_year = ?, language_id = ?,
                original_language_id = ?, rental_duration = ?, rental_rate = ?, length = ?, 
                replacement_cost = ?, rating = ?, special_features = ?, last_update = ?
            where film_id = ?
        """;
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, film.getTitle());
            pstmt.setString(2, film.getDescription());
            pstmt.setInt(3, film.getReleaseYear());
            pstmt.setInt(4, film.getLanguage().getLanguageId());
            pstmt.setNull(5, Types.INTEGER); // original lang id as null
            pstmt.setBigDecimal(6, film.getRentalDuration());
            pstmt.setFloat(7, film.getRentalRate());
            pstmt.setBigDecimal(8, film.getLength());
            pstmt.setFloat(9, film.getReplacementCost());
            pstmt.setString(10, film.getRating());
            pstmt.setString(11, film.getSpecialFeatures());
            pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(13, existsFilm.getFilmId());
            pstmt.executeUpdate();
            
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to update film");
            throw exp;
        }
        return film;
    }

    public long getFilmsTotalCount() throws Exception {
        String query = "select count(*) as total_count from film";
        long totalCount = 0;
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            if (rs.next()) {
                totalCount = rs.getLong("total_count");
            } 
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to count total for films");
            throw exp;
        }
        return totalCount;
    }

    private Integer getInsertId(Connection conn) throws Exception {
        Integer insertId;
        String query = "select LAST_INSERT_ID()";
        try (
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            if (rs.next()) {
                insertId = rs.getInt(1);
            } else {
                throw new RuntimeException("No insert id found!");
            }
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to fetch last insert id");
            throw exp;
        }
        return insertId;
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

    private Film populateFilmFromRs(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setFilmId(rs.getInt("film_id"));
        film.setTitle(rs.getString("title"));
        film.setDescription(rs.getString("description"));
        film.setReleaseYear(rs.getInt("release_year"));

        Language lang = new Language();
        lang.setLanguageId(rs.getInt("language_id"));
        lang.setName(rs.getString("name"));
        film.setLanguage(lang);

        film.setRentalDuration(rs.getBigDecimal("rental_duration"));
        film.setRentalRate(rs.getFloat("rental_rate"));
        film.setLength(rs.getBigDecimal("length"));
        film.setReplacementCost(rs.getFloat("replacement_cost"));
        film.setRating(rs.getString("rating"));
        film.setSpecialFeatures(rs.getString("special_features"));
        return film;
    }
    
}
