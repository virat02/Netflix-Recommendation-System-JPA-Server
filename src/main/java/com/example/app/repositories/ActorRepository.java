package com.example.app.repositories;

import com.example.app.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("SELECT a FROM Actor a WHERE a.actorId=:actorId")
    Optional<Actor> findActorById(@Param("actorId") long a);
}
