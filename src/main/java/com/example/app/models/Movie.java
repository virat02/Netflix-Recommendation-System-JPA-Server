package com.example.app.models;

public class Movie {

    private Long id;
    private String title;
    private String imdbId;
    private String posterUrl;
    private String overview;
    private Long runtime;
    private String releaseDate;
    private Long revenue;
    private String releaseStatus;

    public Movie() { }

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

}
