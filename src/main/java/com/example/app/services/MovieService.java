package com.example.app.services;

import com.example.app.models.*;
import com.example.app.repositories.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CriticRepository criticRepository;

    @Autowired
    private FanRepository fanRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ActorRepository actorRepository;

    // TODO: Read from application.properties
    @Value("${tmdb.api.key}")
    private static String apiKey;

    @Value("${tmdb.api.base.uri}")
    private static String apiBaseUri;

    @Value("${tmdb.image.server}")
    private static String imageServerPath;

    private MovieService() { }

    /**
     * Search TMDb for movies with the given query string
     * @param query a String
     * @return a list of MovieSearchResults
     */
    public static List<MovieSearchResult> searchMovies(String query) {
        String urlString = apiBaseUri + "search/movie?api_key=" + apiKey + "&query=" + query;
        List<MovieSearchResult> searchResults = new ArrayList<>();

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
                    Integer id = ((JSONObject) movie).getInt("id");
                    String title = ((JSONObject) movie).getString("title");
                    String posterUrl = imageServerPath + ((JSONObject) movie).getString("poster_path");

                    searchResults.add(new MovieSearchResult(id, title, posterUrl));
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
                String title = responseJson.getString("title");
                String imdbId = responseJson.getString("imdb_id");
                String posterUrl = imageServerPath + responseJson.getString("poster_path");
                String overview = responseJson.getString("overview");
                Long runtime = responseJson.getLong("runtime");
                String releaseDate = responseJson.getString("release_date");
                Long revenue = responseJson.getLong("revenue");
                String releaseStatus = responseJson.getString("status");

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

    @PostMapping("/api/like/movie/{movieId}/fan/{username}")
    public void likeMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            movie.likedByFan(fan);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/like/fan/{username}/movie/{movieId}")
    public Fan checkIfFanLikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoLike = movie.getLikedByFans();

            if(fansWhoLike.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

    @PostMapping("/api/cast/movie/{movieId}/actor/{actorId}")
    public void castActor(
            @PathVariable("movieId") long movieId,
            @PathVariable("actorId") long actorId){
        if(movieRepository.findById(movieId).isPresent()
                && actorRepository.findById(actorId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            Actor actor = actorRepository.findById(actorId).get();
            movie.castActors(actor);
            movieRepository.save(movie);
        }
    }

    @PostMapping("/api/dislike/movie/{movieId}/fan/{username}")
    public void dislikeMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            movie.dislikedByFan(fan);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/dislike/fan/{username}/movie/{movieId}")
    public Fan checkIfFanDislikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoDisliked = movie.getDislikedByFans();

            if(fansWhoDisliked.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

    @PostMapping("/api/recommend/movie/{movieId}/critic/{username}")
    public void recommendMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            movie.recommendedByCritic(critic);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/recommend/critic/{username}/movie/{movieId}")
    public Critic checkIfCriticRecommendsMovie (
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            List<Critic> criticsWhoRecommended = movie.getRecommendedBy();

            if(criticsWhoRecommended.contains(critic))
            {
                return critic;
            }
        }

        return null;
    }

    @PostMapping("/api/reviews/movie/{movieId}/review/{reviewId}")
    public void reviewMovie(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId) {
        if(movieRepository.findById(movieId).isPresent()
                && reviewRepository.findById(reviewId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            movie.hasReviews(review);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/recommend/movie/{movieId}/recommendedby")
    public List<Critic> listOfCriticsRecommended(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getRecommendedBy();
        }
        return null;
    }

    @GetMapping("/api/like/movie/{movieId}/likedbyfans")
    public List<Fan> listOfFansLikedMovie(
            @PathVariable("movieID") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getLikedByFans();
        }
        return null;
    }

    @GetMapping("/api/dislike/movie/{movieId}/dislikedbyfans")
    public List<Fan> listOfFansDislikedMovie(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getDislikedByFans();
        }
        return null;
    }

    @GetMapping("/api/movie/{movieId}/reviews")
    public List<Review> listOfReviews(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getMovieReview();
        }
        return null;
    }

    @GetMapping("/api/movie/{movieId}/cast")
    public List<Actor> getMovieCast (@PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getListOfActors();
        }
        return null;
    }

}
