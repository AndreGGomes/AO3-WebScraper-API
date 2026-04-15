package com.AndreGGomes.AO3_WebScraper_API.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record WorkDTO(
        String workName, //
        String workID, //
        String rating, //
        List<String> warningList, //
        List<String> categoryList, //
        List<String> fandomList, //
        List<String> relationshipList, //
        List<String> characterList, //
        List<String> additionalTagsList, //
        String workLanguage, //
        LocalDate publishingDate,//
        LocalDate lastUpdateDate,//
        Integer wordCount, //
        Integer workKudos,//
        Integer workHits, //
        String chapters // Must be a string bcause has the format: "10/12"
) {
}
