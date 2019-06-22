package com.example.app.models;

public class ReviewJson {

    private long reviewId;
    private String review;
    private Rating rating;
    private Critic critic;
    private Movie rmovie;

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
    }

    public Movie getRmovie() {
        return rmovie;
    }

    public void setRmovie(Movie rmovie) {
        this.rmovie = rmovie;
    }

}
