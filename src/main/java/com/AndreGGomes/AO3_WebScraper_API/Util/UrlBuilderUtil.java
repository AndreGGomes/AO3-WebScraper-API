package com.AndreGGomes.AO3_WebScraper_API.Util;

public class UrlBuilderUtil {
    private static final String BASE_URL = "https://archiveofourown.org/";

    public static String buildWorkUrl(String workId){
        String finalUrl = "";
        if (workId != null && !workId.isEmpty()){
            finalUrl = BASE_URL + "works/" + workId + "?view_full_work=true";
        }

        return finalUrl;
    }
}
