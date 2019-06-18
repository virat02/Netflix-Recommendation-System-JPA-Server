package com.example.app.controllers;

import com.example.app.models.Actor;
import com.example.app.models.Fan;
import com.example.app.models.Movie;
import com.example.app.repositories.ActorRepository;
import com.example.app.repositories.FanRepository;
import com.example.app.services.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ActorController extends Utils {

    private ActorRepository actorRepository;
    private FanRepository fanRepository;

    @Autowired
    public ActorController(ActorRepository actorRepository, FanRepository fanRepository) {
        this.actorRepository = actorRepository;
        this.fanRepository = fanRepository;
    }

    @PostMapping("/api/actor")
    public Actor createActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @GetMapping("/api/actor")
    public List<Actor> findAllActors(){
        return actorRepository.findAll();
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

//TODO: Search an actor

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
