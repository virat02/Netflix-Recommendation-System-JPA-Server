package com.example.app.controllers;

import com.example.app.models.Movie;
import com.example.app.models.MovieSearchResult;
import com.example.app.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {

    /**
     * Search TMDb for movies with the given query string
     * @param query a String
     * @return a list of MovieSearchResults
     */
    @GetMapping("/api/search/movies")
    List<MovieSearchResult> searchMovies(@RequestParam("query") String query) {
        List<MovieSearchResult> result = new ArrayList<>();

        if(query.length() != 0) {
            result = MovieService.searchMovies(query.replace(" ","+"));
        }

        return result;
    }

    /**
     * Retrieve the TMDb movie with the given id
     * @param id a Long
     * @return the Movie with the given id
     */
    @GetMapping("/api/movies/{id}")
    Movie findMovieById(@PathVariable("id") Long id) {
        return MovieService.findMovieById(id);
    }

}
