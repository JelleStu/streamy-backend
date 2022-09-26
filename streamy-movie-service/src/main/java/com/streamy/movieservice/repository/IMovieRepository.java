package com.streamy.movieservice.repository;

import com.streamy.movieservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;

public interface IMovieRepository extends MongoRepository<Movie, UUID> {
    @Query("{title:'?0'}")
    Movie getMovieByTitle(String title);
}
