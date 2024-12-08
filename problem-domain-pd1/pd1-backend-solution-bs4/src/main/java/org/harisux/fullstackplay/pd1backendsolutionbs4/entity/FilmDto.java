package org.harisux.fullstackplay.pd1backendsolutionbs4.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table(name = "film")
@Data
public class FilmDto {
    
    @Id
    @Column(value = "film_id")
    Integer filmId; 

    @Column(value = "title")
    String title;

    @Column(value = "description")
    String description;

}
