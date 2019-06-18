package com.example.app.models;

import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Critic extends User{

    private String criticDescription;
    private String websiteUrl;

    public Critic() {
        super();
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Recommend",
            joinColumns= @JoinColumn(name="critic_id", referencedColumnName="userId"),
            inverseJoinColumns= @JoinColumn(name= "movie_id", referencedColumnName="movieId"))
    @JsonIgnore
    private List<Movie> recommendedMovies;

    @ManyToMany(mappedBy="criticsFollowed", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> fansFollowingCritics;

    @OneToMany(mappedBy = "critic", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewedMovie;

    public List<Fan> getFansFollowingCritics() {
        return fansFollowingCritics;
    }

    public void setFansFollowingCritics(List<Fan> fansFollowingCritics) {
        this.fansFollowingCritics = fansFollowingCritics;
    }

    public List<Movie> getRecommendedMovies() {
        return recommendedMovies;
    }

    public void setRecommendedMovies(List<Movie> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }

    public String getCriticDescription() {
        return criticDescription;
    }

    public void setCriticDescription(String criticDescription) {
        this.criticDescription = criticDescription;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public List<Review> getReviewedMovie() {
        return reviewedMovie;
    }

    public void setReviewedMovie(List<Review> reviewedMovie) {
        this.reviewedMovie = reviewedMovie;
    }

    public void recommends(Movie movie) {
        this.recommendedMovies.add(movie);
        if(!movie.getRecommendedBy().contains(this)) {
            movie.getRecommendedBy().add(this);
        }
    }

    public void reviews(Review review){
        this.getReviewedMovie().add(review);
        if(review.getCritic()!= this) {
            review.setCritic(this);
        }
    }

    public void set(Critic newCritic) {
        this.firstName = newCritic.firstName != null?
                newCritic.firstName : this.firstName;
        this.lastName = newCritic.lastName != null?
                newCritic.lastName : this.lastName;
        this.username = newCritic.username != null?
                newCritic.username : this.username;
        this.password = newCritic.password != null?
                newCritic.password : this.password;
        this.email = newCritic.email != null?
                newCritic.email : this.email;
        this.dob = newCritic.dob != null?
                newCritic.dob : this.dob;
        this.criticDescription = newCritic.criticDescription != null?
                newCritic.criticDescription : this.criticDescription;
        this.websiteUrl = newCritic.websiteUrl != null?
                newCritic.websiteUrl : this.websiteUrl;
    }
}
