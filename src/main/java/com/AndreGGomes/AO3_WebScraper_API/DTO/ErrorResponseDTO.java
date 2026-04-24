package com.AndreGGomes.AO3_WebScraper_API.DTO;

public record ErrorResponseDTO(
       String timestamp,
       int status,
       String error,
       String path
) {}
