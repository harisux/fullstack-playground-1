package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "language")
@Data
public class Language {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    Integer languageId;

    @Column(name = "name")
    String name;

    @Column(name = "last_update")
    Date lastUpdate;

}
