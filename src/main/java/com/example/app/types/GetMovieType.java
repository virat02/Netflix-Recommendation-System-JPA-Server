package com.example.app.types;

public enum GetMovieType {

    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing"),
    SEARCH("search"),
    SIMILAR("similar");

    private final String getMovieType;

    private GetMovieType(String getMovieType) {
        this.getMovieType = getMovieType;
    }

    @Override
    public String toString() {
        return getMovieType;
    }

}
