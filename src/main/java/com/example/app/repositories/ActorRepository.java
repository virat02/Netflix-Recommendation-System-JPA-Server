package com.example.app.repositories;

import com.example.app.models.Actor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActorRepository extends CrudRepository<Actor, Long> {
	
	@Query("SELECT a FROM Actor a WHERE a.actorId=:actorId")
    Iterable<Actor> findActorById(@Param("actorId") long a);
}