package com.AndreGGomes.AO3_WebScraper_API.DTO;
import java.time.LocalDate;
import java.util.List;

public record UserDTO(
        String userId,
        List<String> pseuds,
        LocalDate joinDate,
        List<String> fandoms,
        Integer numWorks,
        Integer numSeries,
        Integer numBookmarks,
        Integer numCollections,
        Integer numGifts
) {
}
