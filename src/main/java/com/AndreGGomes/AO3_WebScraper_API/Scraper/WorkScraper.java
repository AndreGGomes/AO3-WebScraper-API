package com.AndreGGomes.AO3_WebScraper_API.Scraper;

import com.AndreGGomes.AO3_WebScraper_API.DTO.WorkDTO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.AndreGGomes.AO3_WebScraper_API.Util.ParserUtil.parseDate;
import static com.AndreGGomes.AO3_WebScraper_API.Util.ParserUtil.parseInteger;
import static com.AndreGGomes.AO3_WebScraper_API.Util.UrlBuilderUtil.buildWorkUrl;

@Component
public class WorkScraper {

    public WorkDTO fetchWork(String workId) {
        String finalUrl = buildWorkUrl(workId);

        // Inicia o Document como nulo
        Document doc = null;

        // trying to fetch the HTML from the website
        try {
            Connection.Response response = Jsoup.connect(finalUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Referer", "https://archiveofourown.org/")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Sec-Fetch-Dest", "document")
                    .header("Sec-Fetch-Mode", "navigate")
                    .header("Sec-Fetch-Site", "none")
                    .header("Sec-Fetch-User", "?1")
                    .header("Cache-Control", "max-age=0")
                    .cookie("view_adult", "true")
                    .followRedirects(true)
                    .maxBodySize(0)
                    .timeout(45000)
                    .execute();

            if (response.statusCode() == 404) throw new RuntimeException("Work not found");

            doc = response.parse();

            if (doc.text().contains("This work is only available to registered users")) {
                throw new RuntimeException("Restricted work: Login required");
            }

        } catch (IOException e) {
            IO.println("Website is broken or cannot be connected to: " + e.getMessage());
            return null;
        }

        // scraping the data

        String title = doc.select("h2.title.heading").text().trim();
        String rating = doc.select("dd.rating a.tag").text().trim();
        String workLanguage = doc.select("dd.language").text().trim();

        List<String> warningsList = doc.select("dd.warning.tags ul.commas li a.tag").eachText();
        List<String> categoryList = doc.select("dd.category.tags ul.commas li a.tag").eachText();
        List<String> fandomList = doc.select("dd.fandom.tags ul.commas li a.tag").eachText();
        List<String> relationshipList = doc.select("dd.relationship.tags ul.commas li a.tag").eachText();
        List<String> characterList = doc.select("dd.character.tags ul.commas li a.tag").eachText();
        List<String> additionalTagsList = doc.select("dd.freeform.tags ul.commas li a.tag").eachText();

        String publishingDate = doc.select("dd.stats dl.stats dd.published").text().trim();


        String lastUpdateDate = doc.select("dd.stats dl.stats dd.status").text().trim();

        // if work has only one chapter
        if (lastUpdateDate.isEmpty()) {
            lastUpdateDate = publishingDate; // Se não houver update, a última atualização é a data de publicação
        }

        String wordCount = doc.select("dd.stats dl.stats dd.words").text().trim();
        String kudosCount = doc.select("dd.stats dl.stats dd.kudos").text().trim();
        String hitsCount = doc.select("dd.stats dl.stats dd.hits").text().trim();
        String chapters = doc.select("dd.stats dl.stats dd.chapters").text().trim();

        return new WorkDTO(
                title,
                workId,
                rating,
                warningsList,
                categoryList,
                fandomList,
                relationshipList,
                characterList,
                additionalTagsList,
                workLanguage,
                parseDate(publishingDate).orElse(null),
                parseDate(lastUpdateDate).orElse(null),
                parseInteger(wordCount).orElse(-1),
                parseInteger(kudosCount).orElse(-1),
                parseInteger(hitsCount).orElse(-1),
                chapters
        );
    }

}
