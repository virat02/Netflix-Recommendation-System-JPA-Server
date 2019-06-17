package com.example.app.services;

import com.example.app.Utils;
import com.example.app.models.Actor;
import com.example.app.models.Fan;
import com.example.app.models.Movie;
import com.example.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ActorService extends Utils {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private FanRepository fanRepository;

    @PostMapping("/api/actor")
    public Actor createActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @GetMapping("/api/actor")
    public List<Actor> findAllActors(){
        return (List<Actor>) actorRepository.findAll();
    }

    @GetMapping("/api/actor/{actorId}")
    public Actor findActorById(@PathVariable(name = "actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()){
            return actorRepository.findById(actorId).get();
        }
        return null;
    }

    @PostMapping("/api/follow/actor/{actorId}/fan/{username}")
    public void FanFollowsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            actor.followedBy(fan);
            actorRepository.save(actor);
        }
    }

    @GetMapping("/api/follow/actor/{actorId}/fanfollowing")
    public List<Fan> listOfFansFollowing(
            @PathVariable("actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            return actor.getFansFollowingActor();
        }
        return null;
    }

    /* TODO : Your format of making the json goes here */
//    @GetMapping("/api/search/actor")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public JSONArray getActor(
//            @RequestParam(value = "actorName", required = false) String actorName){
//                String searchUrlString;
//        if(actorName != null)
//            searchUrlString = "https://api.themoviedb.org/3/search/person?api_key="+apiKey+"&query="+actorName.replace(" ","+");
//        else
//            searchUrlString = "https://api.themoviedb.org/3/person/popular?api_key="+apiKey;
//        JSONObject jsonObject;
//        JSONArray jsonArray = new JSONArray();
//
//        try {
//            URL url = new URL(searchUrlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            if(responseCode != 200)
//                throw new RuntimeException("HttpResponseCode from Actor Service: " + responseCode);
//            else{
//                StringBuilder inline = new StringBuilder();
//                Scanner sc = new Scanner(url.openStream());
//                while(sc.hasNext())
//                {
//                    inline.append(sc.nextLine());
//                }
//                sc.close();
//                JSONParser parse = new JSONParser();
//                JSONObject jsonObject1 = (JSONObject) parse.parse(inline.toString());
//                JSONArray results = (JSONArray) jsonObject1.get("results");
//                long[] idArray = new long[results.size()];
//                for(int i=0;i<results.size();i++)
//                {
//                    JSONObject jsonObject2 = (JSONObject)results.get(i);
//                    idArray[i] = (long) jsonObject2.get("id");
//                }
//                Actor actor = new Actor();
//                for(long actorId: idArray){
//                    String getActor = "https://api.themoviedb.org/3/person/"+actorId+"?api_key="+apiKey+"&language=en-US";
//                    URL url1 = new URL(getActor);
//                    HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
//                    connection1.setRequestMethod("GET");
//                    connection1.connect();
//                    inline = new StringBuilder();
//                    Scanner scanner = new Scanner(url1.openStream());
//                    while(scanner.hasNext())
//                    {
//                        inline.append(scanner.nextLine());
//                    }
//                    scanner.close();
//                    jsonObject = (JSONObject) parse.parse(inline.toString());
//                    JSONObject object = new JSONObject();
//                    if(jsonObject.get("birthday") == null)
//                        object.put("dob","-");
//                    else
//                        object.put("dob", jsonObject.get("birthday"));
//                    if(jsonObject.get("deathday") == null)
//                        object.put("dod","-");
//                    else
//                        object.put("dod", jsonObject.get("deathday"));
//                    if(jsonObject.get("imdb_id") == null)
//                        object.put("imdbId","-");
//                    else
//                        object.put("imdbId", jsonObject.get("imdb_id"));
//                    if(jsonObject.get("biography") == null)
//                        object.put("biography","-");
//                    else
//                        object.put("biography", jsonObject.get("biography"));
//                    if(jsonObject.get("popularity") == null)
//                        object.put("actorPopularity","-");
//                    else
//                        object.put("actorPopularity", jsonObject.get("popularity"));
//                    if (jsonObject.get("profile_path") == null)
//                        object.put("profilePicture","-");
//                    else
//                        object.put("profilePicture",imgUrl+jsonObject.get("profile_path").toString());
//
//                    object.put("actorName",jsonObject.get("name"));
//                    object.put("actorId", jsonObject.get("id"));
//
//                    actor.setActorId((Long) object.get("actorId"));
//                    actor.setActorName((String) object.get("actorName"));
//                    actor.setProfilePicture((String) object.get("profilePicture"));
//                    actor.setActorPopularity(object.get("actorPopularity").toString());
//                    actor.setBiography((String) object.get("biography"));
//                    actor.setDob((String) object.get("dob"));
//                    actor.setDod((String) object.get("dod"));
//                    actor.setImdbId((String) object.get("imdbId"));
//
//                    actorRepository.save(actor);
//
//                    jsonArray.add(object);
//                }
//            }
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//        return jsonArray;
//    }

    @GetMapping("/api/actor/{actorId}/moviesActed")
    public List<Movie> getMoviesActed (@PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()){
            Actor actor = actorRepository.findById(actorId).get();
            return actor.getListOfMovies();
        }
        return null;
    }

    @GetMapping("/api/check/follow/fan/{username}/actor/{actorId}")
    public Fan checkIfFanFollowsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoFollowActor = actor.getFansFollowingActor();

            if(fansWhoFollowActor.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

}
