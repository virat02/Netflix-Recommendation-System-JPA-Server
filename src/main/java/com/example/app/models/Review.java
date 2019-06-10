package com.example.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "critic_id")
    @JsonIgnore
    private Critic critic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie rmovie;

    public Review() {
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Critic getCritic() {
        return critic;
    }

    public void setCritic(Critic critic) {
        this.critic = critic;
        if(!critic.getReviewedMovie().contains(this))
            critic.getReviewedMovie().add(this);
    }

    public Movie getRmovie() {
        return rmovie;
    }

    public void setRmovie(Movie rmovie) {
        this.rmovie = rmovie;
        if(!rmovie.getMovieReview().contains(this))
            rmovie.getMovieReview().add(this);
    }

    public void setNewReview(Review newReview){
        this.rating = newReview.rating != null?
                newReview.rating : this.rating;
        this.review = newReview.review != null?
                newReview.review : this.review;
    }
}


