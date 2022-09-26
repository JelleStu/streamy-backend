package com.streamy.movieservice.service;

import com.streamy.movieservice.model.Movie;
import com.streamy.movieservice.repository.IMovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private IMovieRepository movieRepository;

    public Movie getMovieByName(String title){
        return movieRepository.getMovieByTitle(title);
    }

    public Movie addMovie(Movie movie){
        if (movie != null){
            movie.setId(UUID.randomUUID());
            log.info("Saving movie with name {}", movie.getTitle());
             return movieRepository.save(movie);
        }
        else{
            log.error("Movie is null or already exists in database");
        }
        return null;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(String videoId) {
        UUID uuid = UUID.fromString(videoId);
        Optional<Movie> movie = movieRepository.findById(uuid);
        return movie.orElse(null);
    }
}
