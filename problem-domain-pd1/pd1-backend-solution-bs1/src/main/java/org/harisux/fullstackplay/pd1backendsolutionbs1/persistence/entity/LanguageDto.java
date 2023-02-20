package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.UpdateTimestamp;

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
public class LanguageDto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    Integer languageId;

    @Column(name = "name", columnDefinition = "char")
    String name;

    @UpdateTimestamp
    @Column(name = "last_update")
    OffsetDateTime lastUpdate;

}
