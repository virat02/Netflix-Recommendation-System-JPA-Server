package com.example.app.controllers;

import com.example.app.models.Critic;
import com.example.app.models.Movie;
import com.example.app.models.Review;
import com.example.app.repositories.CriticRepository;
import com.example.app.repositories.MovieRepository;
import com.example.app.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private MovieRepository movieRepository;
    private CriticRepository criticRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, MovieRepository movieRepository,
                            CriticRepository criticRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.criticRepository = criticRepository;
    }

    @GetMapping("/api/review")
    public List<Review> findAllReview(){
        return reviewRepository.findAll();
    }

    @PostMapping("/api/review")
    public Review createUser(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @PostMapping("/api/reviews/review/{reviewId}/movie/{movieId}")
    public void reviwedMovie(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId){
        if(movieRepository.findById(movieId).isPresent()
                && reviewRepository.findById(reviewId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            review.setRmovie(movie);
            reviewRepository.save(review);
        }
    }

    @PostMapping("/api/reviews/review/{reviewId}/critic/{username}/movie/{movieId}")
    public void reviewedByCritic(
            @PathVariable("username") String username,
            @PathVariable("reviewId") long reviewId,
            @PathVariable("movieId") long movieId){
        if(criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()
                && reviewRepository.findById(reviewId).isPresent()
                && movieRepository.findById(movieId).isPresent()) {
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            review.setCritic(critic);
            review.setRmovie(movie);
            reviewRepository.save(review);
        }
    }

    @GetMapping("/api/review/{reviewId}/movie")
    public Movie getMovieByReview(@PathVariable("reviewId") long reviewId){
        if(reviewRepository.findById(reviewId).isPresent()){
            Review review = reviewRepository.findById(reviewId).get();
            return review.getRmovie();
        }
        return null;
    }
}
