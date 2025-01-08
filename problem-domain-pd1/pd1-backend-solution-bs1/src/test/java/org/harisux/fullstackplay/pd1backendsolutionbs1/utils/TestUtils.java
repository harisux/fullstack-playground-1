package org.harisux.fullstackplay.pd1backendsolutionbs1.utils;

import java.io.File;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtils {

    public static <T> T getJsonSample(String jsonFilePath, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); //Issue with OffsetDateTime fields
        File jsonFile = null;
        T deserializedObj = null;
        try {
            jsonFile = new ClassPathResource(jsonFilePath).getFile();
            deserializedObj = mapper.readValue(jsonFile, valueType);
        } catch(Exception exp) {
            String errMsg = String.format(
                "Failed to load/parse json file %s!", jsonFilePath);
            log.error(errMsg, exp);
        }
        return deserializedObj;
    }

}
