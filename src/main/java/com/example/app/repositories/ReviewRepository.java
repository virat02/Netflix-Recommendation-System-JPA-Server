package com.example.app.repositories;

import com.example.app.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewId=:reviewId")
    Iterable<Review> findReviewById(@Param("reviewId") long r);
}
