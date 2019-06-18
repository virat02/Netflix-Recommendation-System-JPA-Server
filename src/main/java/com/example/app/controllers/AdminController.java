package com.example.app.controllers;

import com.example.app.models.*;
import com.example.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {

    private AdminRepository adminRepository;
    private ActorRepository actorRepository;
    private CriticRepository criticRepository;
    private FanRepository fanRepository;
    private MovieRepository movieRepository;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository, ActorRepository actorRepository,
                           CriticRepository criticRepository, FanRepository fanRepository,
                           MovieRepository movieRepository, ReviewRepository reviewRepository,
                           UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.actorRepository = actorRepository;
        this.criticRepository = criticRepository;
        this.fanRepository = fanRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/admin")
    public List<Admin> findAllAdmin(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "password", required = false) String password){
        if(username != null && password !=null)
            return (List<Admin>) adminRepository.findAdminByCredentials(username, password);
        return adminRepository.findAll();
    }

    @PostMapping("/api/admin")
    public Admin createAdmin(@RequestBody Admin admin){
        return adminRepository.save(admin);
    }

    @DeleteMapping("/api/delete/fan/{username}")
    public void deleteFan(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent())
            fanRepository.deleteById(fanRepository.findFanIdByUsername(username));
    }

    @DeleteMapping("/api/delete/critic/{username}")
    public void deleteCritic(
            @PathVariable("username") String username){
        if(criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent())
            criticRepository.deleteById(criticRepository.findCriticIdByUsername(username));
    }

    @DeleteMapping("/api/delete/movie/{movieId}")
    public void deleteMovie(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            movieRepository.deleteById(movieId);
        }
    }

    @DeleteMapping("/api/delete/actor/{actorId}")
    public void deleteActor(
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()){
            actorRepository.deleteById(actorId);
        }
    }

    @DeleteMapping("/api/delete/review/{reviewId}")
    public void deleteReview(
            @PathVariable("reviewId") long reviewId){
        if(reviewRepository.findById(reviewId).isPresent()){
            reviewRepository.deleteById(reviewId);
        }
    }

    @PutMapping("/api/edit/fan/{username}")
    public Fan updateFan(
            @PathVariable("username") String username,
            @RequestBody Fan newFan) {
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            fan.set(newFan);
            return fanRepository.save(fan);
        }
        return null;
    }

    @PutMapping("/api/edit/critic/{username}")
    public Critic updateCritic(
            @PathVariable("username") String username,
            @RequestBody Critic newCritic) {
        if(criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()) {
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            critic.set(newCritic);
            return criticRepository.save(critic);
        }
        return null;
    }


    @PutMapping("/api/edit/review/{reviewId}")
    public Review updateReview(
            @PathVariable("reviewId") long reviewId,
            @RequestBody Review newReview){
        if(reviewRepository.findById(reviewId).isPresent()){
            Review review = reviewRepository.findById(reviewId).get();
            review.setNewReview(newReview);
            return reviewRepository.save(review);
        }
        return null;
    }

    @PutMapping("/api/edit/movie/{movieId}")
    public Movie updateMovie(
            @PathVariable("movieId") long movieId,
            @RequestBody Movie newMovie){
        if(movieRepository.findById(movieId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            movie.setMovie(newMovie);
            return movieRepository.save(movie);
        }
        return null;
    }

    @PutMapping("/api/edit/actor/{actorId}")
    public Actor updateActor(
            @PathVariable("actorId") long actorId,
            @RequestBody Actor newActor){
        if(actorRepository.findById(actorId).isPresent()){
            Actor actor = actorRepository.findById(actorId).get();
            actor.setActor(newActor);
            return actorRepository.save(actor);
        }
        return null;
    }
}
