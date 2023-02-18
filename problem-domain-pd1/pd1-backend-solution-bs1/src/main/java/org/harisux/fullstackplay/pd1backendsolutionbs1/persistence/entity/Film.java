package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import java.util.Date;

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
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    Integer filmId;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "release_year")
    Integer releaseYear;

    @ManyToOne()
    @JoinColumn(name = "language_id")
    Language language;

    @ManyToOne()
    @JoinColumn(name = "original_language_id")
    Language originalLanguage;

    @Column(name = "rental_duration")
    Integer rentalDuration;

    @Column(name = "rental_rate")
    Double rentalRate;

    @Column(name = "length")
    Integer length;

    @Column(name = "replacement_cost")
    Double replacementCost;

    @Column(name = "rating")
    String rating;

    @Column(name = "special_features")
    String specialFeatures;

    @Column(name = "last_update")
    Date lastUpdate;

}
