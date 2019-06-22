package com.example.app.repositories;

import com.example.app.models.Critic;
import com.example.app.models.Movie;
import com.example.app.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewId=:reviewId")
    Iterable<Review> findReviewById(@Param("reviewId") long r);

}
