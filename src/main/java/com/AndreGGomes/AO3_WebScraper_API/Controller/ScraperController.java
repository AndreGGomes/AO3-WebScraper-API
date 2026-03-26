package com.AndreGGomes.AO3_WebScraper_API.Controller;

import com.AndreGGomes.AO3_WebScraper_API.DTO.WorkDTO;
import com.AndreGGomes.AO3_WebScraper_API.Service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScraperController {
    @Autowired
    private final ScraperService scraperService;


    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/work/{id}")
    public ResponseEntity<WorkDTO> getWork(@PathVariable String id){
        try {
            WorkDTO work = scraperService.getWorkById(id);
            return ResponseEntity.ok(work);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
