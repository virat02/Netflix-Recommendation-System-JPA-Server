package com.example.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;

@Entity
public class Fan extends User {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="ActorsFollowed",
            joinColumns= @JoinColumn(name="fan_id", referencedColumnName="userId"),
            inverseJoinColumns= @JoinColumn(name= "actor_id", referencedColumnName="actorId"))
    @JsonIgnore
    private List<Actor> actorsFollowed;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="CriticsFollowed",
            joinColumns= @JoinColumn(name="fan_id", referencedColumnName="userId"),
            inverseJoinColumns= @JoinColumn(name= "critic_id", referencedColumnName="userId"))
    @JsonIgnore
    private List<Critic> criticsFollowed;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Likes",
            joinColumns= @JoinColumn(name="fan_id", referencedColumnName="userId"),
            inverseJoinColumns= @JoinColumn(name= "movie_id", referencedColumnName="movieId"))
    @JsonIgnore
    private List<Movie> likesMovies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Dislikes",
            joinColumns = @JoinColumn(name = "fan_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"))
    @JsonIgnore
    private List<Movie> dislikesMovies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "FansFollowed",
            joinColumns = @JoinColumn(name = "userId1", referencedColumnName = "userId"))
    @JsonIgnore
    private List<Fan> followingFans;

    @ManyToMany(mappedBy = "followingFans", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> followedByFans;

    public Fan() {
        super();
    }

    public List<Actor> getActorsFollowed() {
        return actorsFollowed;
    }

    public void setActorsFollowed(List<Actor> actorsFollowed) {
        this.actorsFollowed = actorsFollowed;
    }

    public List<Critic> getCriticsFollowed() {
        return criticsFollowed;
    }

    public void setCriticsFollowed(List<Critic> criticsFollowed) {
        this.criticsFollowed = criticsFollowed;
    }

    public List<Movie> getLikesMovies() {
        return likesMovies;
    }

    public void setLikesMovies(List<Movie> likesMovies) {
        this.likesMovies = likesMovies;
    }

    public List<Movie> getDislikesMovies() {
        return dislikesMovies;
    }

    public void setDislikesMovies(List<Movie> dislikesMovies) {
        this.dislikesMovies = dislikesMovies;
    }

    public List<Fan> getFollowingFans() {
        return followingFans;
    }

    public void setFollowingFans(List<Fan> followingFans) {
        this.followingFans = followingFans;
    }

    public List<Fan> getFollowedByFans() {
        return followedByFans;
    }

    public void setFollowedByFans(List<Fan> followedByFans) {
        this.followedByFans = followedByFans;
    }

    public void likesMovie(Movie movie) {
        this.likesMovies.add(movie);
        if(!movie.getLikedByFans().contains(this)) {
            movie.getLikedByFans().add(this);
        }
    }

    public void dislikesMovie(Movie movie) {
        this.dislikesMovies.add(movie);
        if(!movie.getDislikedByFans().contains(this)) {
            movie.getDislikedByFans().add(this);
        }
    }

    public void followsActor(Actor actor) {
        this.actorsFollowed.add(actor);
        if(!actor.getFansFollowingActor().contains(this)) {
            actor.getFansFollowingActor().add(this);
        }
    }

    public void followsCritic(Critic critic) {
        this.criticsFollowed.add(critic);
        if(!critic.getFansFollowingCritics().contains(this)) {
            critic.getFansFollowingCritics().add(this);
        }
    }

    public void followsFan(Fan fan){
        this.getFollowingFans().add(fan);
        if(!fan.getFollowedByFans().contains(this))
            fan.getFollowedByFans().add(this);
    }

    public void set(Fan newFan) {
        this.firstName = newFan.firstName != null?
                newFan.firstName : this.firstName;
        this.lastName = newFan.lastName != null?
                newFan.lastName : this.lastName;
        this.username = newFan.username != null?
                newFan.username : this.username;
        this.password = newFan.password != null?
                newFan.password : this.password;
        this.email = newFan.email != null?
                newFan.email : this.email;
        this.dob = newFan.dob != null?
                newFan.dob : this.dob;
    }
}
