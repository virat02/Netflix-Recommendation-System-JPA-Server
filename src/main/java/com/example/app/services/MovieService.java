package com.example.app.services;

import com.example.app.models.Movie;
import com.example.app.types.GetMovieType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {

    // TODO: Read from application.properties
//    @Value("${tmdb.api.key}")
    private static String apiKey = "5d837b926bca6fa72fb4cf1b287a1bfa";
//    @Value("${tmdb.api.base.uri}")
    private static String apiBaseUri = "https://api.themoviedb.org/3/";
//    @Value("${tmdb.image.server}")
    private static String imageServerPath = "https://image.tmdb.org/t/p/w500";

    private MovieService() { }

    /**
     *
     * @param getMovieType
     * @param lang
     * @param region
     * @param query
     * @param pageNo
     * @return
     */
    public static List<Movie> getMovies(GetMovieType getMovieType, String lang, String region, String query,
                                                    String pageNo, Long movieId) {
        String urlString = apiBaseUri;

        switch(getMovieType) {
            case SEARCH:
                urlString += getMovieType.toString() + "/movie";
                break;
            case SIMILAR:
                urlString += "movie/" + movieId + "/" + getMovieType.toString();
                break;
            default:
                urlString += "movie/" + getMovieType.toString();
        }

        urlString += "?api_key=" + apiKey;
        urlString += (lang != null) ? ("&language=" + lang) : "";
        urlString += (region != null) ? ("&region=" + region) : "";
        urlString += (query != null) ? ("&query=" + query) : "";
        urlString += (pageNo != null) ? ("&page=" + pageNo) : "";

        return getMovies(urlString);
    }

    /**
     *
     * @param urlString
     * @return
     */
    private static List<Movie> getMovies(String urlString) {
        List<Movie> searchResults = new ArrayList<>();

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if(conn.getResponseCode() == 200) {
                StringBuilder responseString = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while(sc.hasNext()) {
                    responseString.append(sc.nextLine());
                }
                sc.close();

                JSONObject responseJson = new JSONObject(responseString.toString());
                JSONArray results = responseJson.getJSONArray("results");

                for(Object movie : results) {
                    JSONObject movieJsonObj = (JSONObject) movie;

                    Long id = new Long((Integer) getJsonObjectValue(movieJsonObj, "id"));
                    String title = "" + getJsonObjectValue(movieJsonObj, "title");
                    String posterUrl = imageServerPath + getJsonObjectValue(movieJsonObj, "poster_path");
                    String overview = "" + getJsonObjectValue(movieJsonObj, "overview");
                    String releaseDate = "" + getJsonObjectValue(movieJsonObj, "release_date");

                    if(!posterUrl.contains("null")) {
                        searchResults.add(new Movie(id, title, null, posterUrl, overview, null, releaseDate,
                                null, null));
                    }
                }
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }

        return searchResults;
    }

    /**
     * Retrieve the TMDb movie with the given id
     * @param id a Long
     * @return the Movie with the given id
     */
    public static Movie findMovieById(Long id) {
        String urlString = apiBaseUri + "movie/" + id + "?api_key=" + apiKey;
        Movie movie = null;

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if(conn.getResponseCode() == 200) {
                StringBuilder responseString = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while(sc.hasNext()) {
                    responseString.append(sc.nextLine());
                }
                sc.close();

                JSONObject responseJson = new JSONObject(responseString.toString());
                String title = "" + getJsonObjectValue(responseJson, "title");
                String imdbId = "" + getJsonObjectValue(responseJson, "imdb_id");
                String posterUrl = imageServerPath + getJsonObjectValue(responseJson, "poster_path");
                String overview = "" + getJsonObjectValue(responseJson, "overview");
                Long runtime = getJsonObjectValue(responseJson, "runtime") != null ?
                        new Long((Integer) getJsonObjectValue(responseJson, "runtime")): null;
                String releaseDate = "" + getJsonObjectValue(responseJson, "release_date");
                Long revenue = Long.parseLong("" + getJsonObjectValue(responseJson, "revenue"));
                String releaseStatus = "" + getJsonObjectValue(responseJson, "status");

                movie = new Movie(id, title, imdbId, posterUrl, overview, runtime, releaseDate, revenue, releaseStatus);
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return movie;
    }

    private static Object getJsonObjectValue(JSONObject jsonObject, String key) {
        Object value = null;
        if(jsonObject.has(key) && !jsonObject.isNull(key)) {
            value = jsonObject.get(key);
        }
        return value;
    }

}
