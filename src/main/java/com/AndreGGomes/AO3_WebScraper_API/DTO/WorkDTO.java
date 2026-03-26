package com.AndreGGomes.AO3_WebScraper_API.DTO;

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
        String publishingDate,//
        String lastUpdateDate,//
        String wordCount, //
        String workKudos,//
        String workHits, //
        String chapters // // Ex: "10 / 12"
) {
}
