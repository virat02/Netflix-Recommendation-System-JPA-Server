package com.example.app.services;

import com.example.app.models.Actor;
import com.example.app.models.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActorService {

    private static String apiKey = "5d837b926bca6fa72fb4cf1b287a1bfa";
    private static String apiBaseUri = "https://api.themoviedb.org/3/";
    private static String imageServerPath = "https://image.tmdb.org/t/p/w500";

    private ActorService() { }

    public static List<Actor> searchActors(String query, String lang, String region, String pageNo) {
        List<Actor> searchResults = new ArrayList<>();

        String urlString = apiBaseUri + "search/person";
        urlString += "?api_key=" + apiKey;
        urlString += (lang != null) ? ("&language=" + lang) : "";
        urlString += (region != null) ? ("&region=" + region) : "";
        urlString += (query != null) ? ("&query=" + query) : "";
        urlString += (pageNo != null) ? ("&page=" + pageNo) : "";

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

                for(Object actor : results) {
                    JSONObject actorJsonObj = (JSONObject) actor;

                    Long actorId = new Long((Integer) getJsonObjectValue(actorJsonObj, "id"));
                    String actorName = "" + getJsonObjectValue(actorJsonObj, "name");
                    String profilePicture = imageServerPath + getJsonObjectValue(actorJsonObj, "profile_path");
                    String actorPopularity = "" + getJsonObjectValue(actorJsonObj, "popularity");
                    JSONArray knownFor = (JSONArray) getJsonObjectValue(actorJsonObj, "known_for");
                    List<Movie> movies = new ArrayList<>();

                    for(Object movie : knownFor) {
                        JSONObject movieJsonObj = (JSONObject) movie;
                        Long movieId = new Long((Integer) getJsonObjectValue(movieJsonObj, "id"));
                        String title = "" + getJsonObjectValue(movieJsonObj, "title");
                        String posterUrl = imageServerPath + getJsonObjectValue(movieJsonObj, "poster_path");
                        String overview = "" + getJsonObjectValue(movieJsonObj, "overview");
                        String releaseDate = "" + getJsonObjectValue(movieJsonObj, "release_date");

                        movies.add(new Movie(movieId, title, null, posterUrl, overview, null,
                                releaseDate, null, null));
                    }

                    if(!profilePicture.contains("null")) {
                        searchResults.add(new Actor(actorId, actorName, null, null, null, null,
                                actorPopularity, profilePicture, null, null, movies));
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

    public static Actor findActorById(Long id) {
        String urlString = apiBaseUri + "person/" + id + "?api_key=" + apiKey;
        Actor actor = null;

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
                Long actorId = new Long((Integer) getJsonObjectValue(responseJson, "id"));
                String actorName = "" + getJsonObjectValue(responseJson, "name");
                String dob = "" + getJsonObjectValue(responseJson, "birthday");
                String dod = "" + getJsonObjectValue(responseJson, "deathday");
                String imdbId = "" + getJsonObjectValue(responseJson, "imdb_id");
                String biography = "" + getJsonObjectValue(responseJson, "biography");
                String popularity = "" + getJsonObjectValue(responseJson, "popularity");
                String profilePicture = imageServerPath + getJsonObjectValue(responseJson, "profile_path");
                String wikilink = "https://wikipedia.org/wiki/";

                for(String n : actorName.split(" ")) {
                    wikilink += n + "_";
                }

                wikilink = wikilink.substring(0, wikilink.length() - 1);

                actor = new Actor(actorId, actorName, dob, dod, imdbId, biography, popularity, profilePicture, wikilink,
                        null, null);
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return actor;
    }

    private static Object getJsonObjectValue(JSONObject jsonObject, String key) {
        Object value = null;
        if(jsonObject.has(key) && !jsonObject.isNull(key)) {
            value = jsonObject.get(key);
        }
        return value;
    }

}
