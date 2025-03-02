package com.java.project.llms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.project.llms.service.MovieRecommendationService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieRecommendationService movieService;

    @GetMapping("/recommendations")
    public String getRecommendations() throws Exception {
        return movieService.getRecommendations();
    }
}