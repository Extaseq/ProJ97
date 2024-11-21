package com.nichga.proj97.Util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }

        String dateString = json.getAsString();

        // Handle partial dates such as "2013" (year only) or "2013-05" (year and month)
        try {
            if (dateString.length() == 4) { // Only year provided (e.g., "2013")
                return LocalDate.parse(dateString + "-01-01", DATE_FORMATTER); // Default to 01-01
            } else if (dateString.length() == 7) { // Year and month (e.g., "2013-05")
                return LocalDate.parse(dateString + "-01", DATE_FORMATTER); // Default to 01 day of the month
            } else {
                return LocalDate.parse(dateString, DATE_FORMATTER); // Full date provided (e.g., "2013-05-14")
            }
        } catch (DateTimeParseException e) {
            throw new JsonParseException("Failed to parse LocalDate: " + dateString, e);
        }
    }
}
