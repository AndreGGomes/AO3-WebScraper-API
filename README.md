# Unofficial AO3 REST Web Scraper API

An unofficial REST API built with **Java 25** and **Spring Boot** designed to scrape and extract metadata from **Archive of Our Own (AO3)**.

Currently, the API supports full data extraction of a single work using its unique **Work ID**, returning the results in a structured **JSON** format.

> **Disclaimer:** This project is not affiliated with or endorsed by OTW or AO3. It is intended for educational and personal use. Please be mindful of AO3's Terms of Service and avoid aggressive scraping that could strain their servers.

---

## Features
- **Work Metadata Extraction:** Get title, author, rating, tags, fandoms, and statistics.
- **JSON Output:** Clean and structured data ready for integration with other applications.
- **Adult Content Bypass:** Automatically handles the "Adult Content" interstitial warning page.
- **Full Work Support:** Uses the `view_full_work` parameter to ensure all metadata is captured regardless of chapter count.
- **Cloudflare Resilience:** Optimized headers and IPv4 priority to minimize connection timeouts.

## Tech Stack
- **Java 25**
- **Spring Boot 4.0.4**
- **Jsoup** (HTML Parsing)
- **Maven** (Dependency Management)

## API instalation

Clone the repo with `git clone git@github.com:AndreGGomes/AO3-WebScraper-API.git`

run `./mvnw spring-boot:run`

## API Usage

### Get Work Details
Fetch all available metadata for a specific work.

**Endpoint:** `GET /work/{workId}`

**Example Request:** `GET http://localhost:8080/work/10057010`

**Example JSON Response:**
```json
{
  {
  "workName": "All the Young Dudes",
  "workID": "10057010",
  "rating": "Mature",
  "warningList": [
    "No Archive Warnings Apply",
    "Major Character Death",
    "Graphic Depictions Of Violence"
  ],
  "categoryList": [
    "M/M"
  ],
  "fandomList": [
    "Harry Potter - J. K. Rowling"
  ],
  "relationshipList": [
    "Sirius Black/Remus Lupin",
    "Sirius Black & Remus Lupin",
    "James Potter/Lily Evans Potter"
  ],
  "characterList": [
    "Remus Lupin",
    "Sirius Black",
    "James Potter",
    "Lily Evans Potter",
    "Peter Pettigrew",
    "Severus Snape",
    "Minerva McGonagall",
    "Bellatrix Black Lestrange",
    "Narcissa Black Malfoy",
    "Albus Dumbledore",
    "Mulciber Sr. (Harry Potter)",
    "Horace Slughorn",
    "Mary Macdonald",
    "Marlene McKinnon",
    "Poppy Pomfrey",
    "Walburga Black",
    "Regulus Black",
    "Fenrir Greyback"
  ],
  "additionalTagsList": [
    "Marauders' Era",
    "Marauders",
    "Marauders Friendship",
    "wolfstar",
    "Get Together",
    "Slow Burn",
    "so slow",
    "it's slow",
    "seriously",
    "Complete",
    "Canon Compliant",
    "Angst",
    "Fluff",
    "Fluff and Angst",
    "Requited Love",
    "Canonical Character Death",
    "First War with Voldemort",
    "First Kiss",
    "Period Typical Attitudes"
  ],
  "workLanguage": "English",
  "publishingDate": "2017-03-02",
  "lastUpdateDate": "2018-11-12",
  "wordCount": "526,969",
  "workKudos": "301,995",
  "workHits": "19,617,865",
  "chapters": "188/188"
}
}
