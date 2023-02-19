package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rating {
    G("G"), 
    PG("PG"), 
    PG13("PG-13"), 
    R("R"), 
    NC17("NC-17");

    private String label;
}
