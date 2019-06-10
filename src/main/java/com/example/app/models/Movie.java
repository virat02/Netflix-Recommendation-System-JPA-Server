package com.example.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    private Long id;

    private String title;
    private String imdbId;
    private String posterUrl;

    private double imdbRating;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private Long runtime;
    private String releaseDate;
    private Long revenue;
    private String releaseStatus;

    @ManyToMany(mappedBy = "recommendedMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Critic> recommendedBy;

    @ManyToMany(mappedBy = "likesMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> likedByFans;

    @ManyToMany(mappedBy = "dislikesMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> dislikedByFans;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MovieCast",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actorId"))
    @JsonIgnore
    private List<Actor> listOfActors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Genre> listOfGenres;

    @OneToMany(mappedBy = "rmovie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> movieReview;

    public Movie() { }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public List<Critic> getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(List<Critic> recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public List<Fan> getLikedByFans() {
        return likedByFans;
    }

    public void setLikedByFans(List<Fan> likedByFans) {
        this.likedByFans = likedByFans;
    }

    public List<Fan> getDislikedByFans() {
        return dislikedByFans;
    }

    public void setDislikedByFans(List<Fan> dislikedByFans) {
        this.dislikedByFans = dislikedByFans;
    }

    public List<Actor> getListOfActors() {
        return listOfActors;
    }

    public void setListOfActors(List<Actor> listOfActors) {
        this.listOfActors = listOfActors;
    }

    public List<Genre> getListOfGenres() {
        return listOfGenres;
    }

    public void setListOfGenres(List<Genre> listOfGenres) {
        this.listOfGenres = listOfGenres;
    }

    public List<Review> getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(List<Review> movieReview) {
        this.movieReview = movieReview;
    }

    public Movie(Long id, String title, String imdbId, String posterUrl, String overview, Long runtime,
                 String releaseDate, Long revenue, String releaseStatus) {
        this.id = id;
        this.title = title;
        this.imdbId = imdbId;
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.releaseStatus = releaseStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String posterUrl) {
        this.overview = overview;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public void likedByFan(Fan fan) {
        this.likedByFans.add(fan);
        if(!fan.getLikesMovies().contains(this)) {
            fan.getLikesMovies().add(this);
        }
    }

    public void dislikedByFan(Fan fan) {
        this.dislikedByFans.add(fan);
        if(!fan.getDislikesMovies().contains(this)) {
            fan.getDislikesMovies().add(this);
        }
    }

    public void recommendedByCritic(Critic critic) {
        this.recommendedBy.add(critic);
        if(!critic.getRecommendedMovies().contains(this)) {
            critic.getRecommendedMovies().add(this);
        }
    }

    public void hasReviews(Review review) {
        this.movieReview.add(review);
        if(review.getRmovie() != this) {
            review.setRmovie(this);
        }
    }

    public void hasGenres (Genre genre) {
        this.listOfGenres.add(genre);
        if(genre.getMovie() != this) {
            genre.setMovie(this);
        }
    }

    public void castActors(Actor actor){
        this.listOfActors.add(actor);
        if(!actor.getListOfMovies().contains(this))
            actor.getListOfMovies().add(this);
    }

    public void setMovie(Movie newMovie){
        this.title = newMovie.title != null?
                newMovie.title : this.title;
        this.imdbId = newMovie.imdbId != null?
                newMovie.imdbId : this.imdbId;
        this.imdbRating = newMovie.imdbRating >= 0?
                newMovie.imdbRating : this.imdbRating;
        this.overview = newMovie.overview != null?
                newMovie.overview : this.overview;
        this.posterUrl = newMovie.posterUrl != null?
                newMovie.posterUrl : this.posterUrl;
        this.releaseDate = newMovie.releaseDate != null?
                newMovie.releaseDate : this.releaseDate;
        this.releaseStatus = newMovie.releaseStatus != null?
                newMovie.releaseStatus : this.releaseStatus;
        this.revenue = newMovie.revenue >= 0?
                newMovie.revenue : this.revenue;
        this.runtime = newMovie.runtime >= 0?
                newMovie.runtime : this.runtime;
    }

}
