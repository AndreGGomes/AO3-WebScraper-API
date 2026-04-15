package com.AndreGGomes.AO3_WebScraper_API.Util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ParserUtil {

    // Necessary to transform 999,999 into 999999
    public static Optional<Integer> parseInteger(String value) {
        if (value == null || value.isEmpty()) return Optional.empty();
        try {
            return Optional.of(Integer.parseInt(value.replace(",", "").trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<LocalDate> parseDate(String value){
        if (value == null || value.isEmpty()) return Optional.empty();
        try{
            return Optional.of(LocalDate.parse(value));
        } catch(DateTimeParseException e){
            return Optional.empty();
        }
    }
}
