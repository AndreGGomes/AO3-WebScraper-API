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

@Component
public class WorkScraper {
    private static final String BASE_URL = "https://archiveofourown.org/works/";

    public WorkDTO fetchWork(String workId) {
        String finalUrl = buildUrl(workId);

        // Inicia o Document como nulo
        Document doc = null;

        // trying to fetch the html from the website
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
                publishingDate,
                lastUpdateDate,
                wordCount,
                kudosCount,
                hitsCount,
                chapters
        );
    }

    // for printing in the terminal
    public void test(String workId){
        String finalUrl = buildUrl(workId);
        List<WorkDTO> results = new ArrayList<>();

        boolean docCreated;
        Document doc = null;

        try {
            Connection.Response response = Jsoup.connect(finalUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Referer", "https://archiveofourown.org/")
                    // Headers de segurança que o Cloudflare verifica:
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

            docCreated = true;
        } catch (IOException e){
            docCreated = false;
            IO.println("Website is broken or cannot be connected to: " + e.getMessage());
        }

        if (docCreated) {
            String title = doc.select("h2.title.heading").text();
            IO.println("title: " + title);
            String rating = doc.select("dd.rating a.tag").text();
            IO.println("rating: " + rating);
            // already have workId

            // getting warnings
            List<String> warningsList = new ArrayList<>();
            Elements warningElements = doc.select("dd.warning.tags ul.commas li a.tag");
            IO.println("warnings: ");
            for (Element w : warningElements){
                String warningText = w.text().trim();
                warningsList.add(warningText);
                IO.print(warningText + " - ");
            }

            //getting categories
            List<String> categoryList = new ArrayList<>();
            IO.println();
            IO.println("categories: ");
            Elements categoryElements = doc.select("dd.category.tags ul.commas li a.tag");
            for (Element c : categoryElements){
                String categoryText = c.text().trim();
                categoryList.add(categoryText);
                IO.print(categoryText + " - ");
            }

            //getting fandom
            List<String> fandomList = new ArrayList<>();
            IO.println();
            IO.println("Fandoms: ");
            Elements fandomElements = doc.select("dd.fandom.tags ul.commas li a.tag");
            for (Element f : fandomElements){
                String fandomText = f.text().trim();
                fandomList.add(fandomText);
                IO.print(fandomText + " - ");
            }

            //getting relatioships

            List<String> relationshipList = new ArrayList<>();
            IO.println();
            IO.println("relationships: ");
            Elements relationshipElements = doc.select("dd.relationship.tags ul.commas li a.tag");
            for (Element r : relationshipElements){
                String relationshipText = r.text().trim();
                relationshipList.add(relationshipText);
                IO.print(relationshipText + " - ");
            }

            //getting characters
            List<String> characterList = new ArrayList<>();
            IO.println();
            IO.println("characters: ");
            Elements characterElements = doc.select("dd.character.tags ul.commas li a.tag");
            for (Element c : characterElements){
                String characterText = c.text().trim();
                characterList.add(characterText);
                IO.print(characterText + " - ");
            }

            // getting additional tags
            List<String> additionalTagsList = new ArrayList<>();
            IO.println();
            IO.println("additional tags: ");
            Elements additionalTagsElements = doc.select("dd.freeform.tags ul.commas li a.tag");
            for (Element a : additionalTagsElements){
                String additionalTagsText = a.text().trim();
                characterList.add(additionalTagsText);
                IO.print(additionalTagsText + " - ");
            }

            // getting language
            String workLanguage = "";
            IO.println();
            IO.println("language: ");
            workLanguage = doc.select("dd.language").text().trim();
            IO.print(workLanguage);

            // getting publishing date
            String publishingDate = "";
            IO.println();
            IO.println("published: ");
            publishingDate = doc.select("dd.stats dl.stats dd.published").text().trim();
            IO.print(publishingDate);

            // getting last time updates
            String lastUpdateDate = "";
            IO.println();
            IO.println("last updated: ");
            lastUpdateDate = doc.select("dd.stats dl.stats dd.status").text().trim();
            IO.print(lastUpdateDate);

            // getting word count
            String wordCount;
            IO.println();
            IO.println("word count: ");
            wordCount = doc.select("dd.stats dl.stats dd.words").text().trim();
            IO.print(wordCount);

            //getting kudos
            String kudosCount;
            IO.println();
            IO.println("kudos count: ");
            kudosCount = doc.select("dd.stats dl.stats dd.kudos").text().trim();
            IO.print(kudosCount);

            //getting hits

            String hitsCount;
            IO.println();
            IO.println("hits count: ");
            hitsCount = doc.select("dd.stats dl.stats dd.hits").text().trim();
            IO.print(hitsCount);

            //getting chapters

            String chapters;
            IO.println();
            IO.println("chapters: ");
            chapters = doc.select("dd.stats dl.stats dd.chapters").text().trim();
            IO.print(chapters);
        }
    }


    private String buildUrl(String workId){
        String finalUrl = "";
        if (workId != null && !workId.isEmpty()){
            finalUrl = BASE_URL + workId + "?view_full_work=true";
        }

        return finalUrl;
    }
}
