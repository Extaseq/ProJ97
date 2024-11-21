package com.nichga.proj97.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nichga.proj97.Model.Books;

import java.time.LocalDate;

public class JacksonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Converter());

    public static Books parseJson(String json) {
        try {
            return objectMapper.readValue(json, Books.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
