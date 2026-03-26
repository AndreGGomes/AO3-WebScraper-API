package com.AndreGGomes.AO3_WebScraper_API.Service;
import com.AndreGGomes.AO3_WebScraper_API.DTO.WorkDTO;
import com.AndreGGomes.AO3_WebScraper_API.Scraper.WorkScraper;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {
    public final WorkScraper workScraper;

    public ScraperService(WorkScraper workScraper) {
        this.workScraper = workScraper;
    }

    public WorkDTO getWorkById(String workId){
        WorkDTO work = workScraper.fetchWork(workId);
        if (work == null){
            throw new RuntimeException("Could not obtain data from work " + workId);
        }
        return work;
    }
}
