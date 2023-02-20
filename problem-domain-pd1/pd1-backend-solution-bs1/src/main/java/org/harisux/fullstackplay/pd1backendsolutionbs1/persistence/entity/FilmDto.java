package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "release_year", columnDefinition = "year")
    Integer releaseYear;

    @ManyToOne()
    @JoinColumn(name = "language_id")
    LanguageDto language;

    @ManyToOne()
    @JoinColumn(name = "original_language_id")
    LanguageDto originalLanguage;

    @Column(name = "rental_duration")
    Integer rentalDuration;

    @Column(name = "rental_rate")
    BigDecimal rentalRate;

    @Column(name = "length")
    Integer length;

    @Column(name = "replacement_cost")
    BigDecimal replacementCost;

    @Column(columnDefinition = "ENUM('G', 'PG', 'PG-13', 'R', 'NC-17')")
    String rating;

    @Column(name = "special_features", columnDefinition = "set")
    String specialFeatures;

    @UpdateTimestamp
    @Column(name = "last_update")
    OffsetDateTime lastUpdate;

}
