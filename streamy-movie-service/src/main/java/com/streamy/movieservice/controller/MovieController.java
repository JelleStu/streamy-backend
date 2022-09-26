package com.streamy.movieservice.controller;

import com.streamy.movieservice.model.Movie;
import com.streamy.movieservice.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/movies")
@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/allmovies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @GetMapping("/moviebytitle")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam(value = "title")String title){
        return ResponseEntity.ok().body(movieService.getMovieByName(title));
    }

    @GetMapping("/movie")
    public ResponseEntity<Movie> getMovieById(@RequestParam(value = "id")String videoId){
        return ResponseEntity.ok().body(movieService.getMovieById(videoId));
    }
    @PostMapping("/addMovie")
    public ResponseEntity addMovie(@RequestBody Movie movie){
        if (movie.getTitle() != null && movie.getThumbnail() != null && movie.getVideoLink() != null) {
            movieService.addMovie(movie);
            return ResponseEntity.ok().body("Movie added");
        }
        else {
            return ResponseEntity.badRequest().body("Missing information");
        }
    }


}
