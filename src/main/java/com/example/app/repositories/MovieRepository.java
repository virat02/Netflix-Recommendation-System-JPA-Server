package com.example.app.repositories;

import com.example.app.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.movieId=:movieId")
    Iterable<Movie> findMovieById(@Param("movieId") long m);

    @Query("SELECT m.title FROM Movie m WHERE m.movieId=:movieId")
    String findMovieNameById(@Param("movieId") long movieId);
}
