package com.java.project.llms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MovieRecommendationService {
    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    public String getRecommendations() throws Exception {
		return tmdbApiKey;
//        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey + "&language=en-US&page=1";
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(url);
//            try (CloseableHttpResponse response = client.execute(request)) {
//                return EntityUtils.toString(response.getEntity());
//            }
//        }
    }
}