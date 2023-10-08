package com.tallerwebi.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final ObjectMapper objectMapper;

    public Mapper() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return objectMapper;
    }
}
