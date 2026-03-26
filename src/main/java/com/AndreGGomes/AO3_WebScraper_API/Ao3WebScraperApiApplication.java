package com.AndreGGomes.AO3_WebScraper_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.AndreGGomes.AO3_WebScraper_API.DTO.WorkDTO;
import com.AndreGGomes.AO3_WebScraper_API.Service.ScraperService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ao3WebScraperApiApplication {

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack" , "true");
		SpringApplication.run(Ao3WebScraperApiApplication.class, args);
	}

}
