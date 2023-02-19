package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "film")
@Data
public class FilmDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    Integer filmId;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "release_year")
    java.sql.Date releaseYear;

    @ManyToOne()
    @JoinColumn(name = "language_id")
    Language language;

    @ManyToOne()
    @JoinColumn(name = "original_language_id")
    Language originalLanguage;

    @Column(name = "rental_duration")
    Integer rentalDuration;

    @Column(name = "rental_rate")
    BigDecimal rentalRate;

    @Column(name = "length")
    Integer length;

    @Column(name = "replacement_cost")
    BigDecimal replacementCost;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('G', 'PG', 'PG-13', 'R', 'NC-17')")
    Rating rating;

    @Column(name = "special_features", columnDefinition = "set")
    Set<String> specialFeatures;

    @Column(name = "last_update")
    OffsetDateTime lastUpdate;

}
