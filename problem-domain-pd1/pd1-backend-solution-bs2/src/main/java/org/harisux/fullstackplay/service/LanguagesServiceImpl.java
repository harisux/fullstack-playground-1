package org.harisux.fullstackplay.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.ArrayList;

import org.jboss.logging.Logger;
import org.openapi.quarkus.sakila_films_crud_yml.model.Film;
import org.openapi.quarkus.sakila_films_crud_yml.model.Language;
import org.openapi.quarkus.sakila_films_crud_yml.model.LanguageList;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LanguagesServiceImpl implements LanguagesService {

    private static final Logger LOG = Logger.getLogger(LanguagesServiceImpl.class);

    @Inject
    AgroalDataSource dataSource;

    @Override
    public LanguageList getLanguages() throws Exception {
        LanguageList languageList = new LanguageList();
        languageList.setData(new ArrayList<>());

        String query = "select language_id, name, last_update from language F";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            while (rs.next()) {
                Language language = new Language();
                language.setLanguageId(rs.getInt("language_id"));
                language.setName(rs.getString("name"));
                language.setLastUpdate(
                    rs.getTimestamp("last_update").toInstant().atOffset(ZoneOffset.UTC));
                languageList.addDataItem(language);
            }
            
        } catch(SQLException exp) {
            LOG.error("Failed to execute query to fetch language list");
            throw exp;
        }

        return languageList;
    }
    
}
