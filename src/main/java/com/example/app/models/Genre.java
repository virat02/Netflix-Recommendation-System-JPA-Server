package com.example.app.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Genre {

    @Id
    private int genreId;

    public Genre() {
        super();
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Movie movie;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        if(!movie.getListOfGenres().contains(this)) {
            movie.getListOfGenres().add(this);
        }
    }

}
