package org.harisux.fullstackplay.pd1backendsolutionbs4.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;   
}
