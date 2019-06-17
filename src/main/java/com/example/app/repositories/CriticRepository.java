package com.example.app.repositories;

import com.example.app.models.Critic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticRepository extends CrudRepository<Critic, Long> {
	
	@Query("SELECT c FROM Critic c WHERE c.username=:username")
    Iterable<Critic> findCriticByUsername(@Param("username") String u);

    @Query("SELECT c FROM Critic c WHERE c.username=:username AND c.password=:password")
    Iterable<Critic> findCriticByCredentials(
    		@Param("username") String username, @Param("password") String password);

    @Query("SELECT c.userId FROM Critic c where c.username=:username")
    long findCriticIdByUsername(@Param("username") String u);
}
