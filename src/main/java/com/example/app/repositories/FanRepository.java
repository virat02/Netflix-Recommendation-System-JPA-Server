package com.example.app.repositories;

import com.example.app.models.Fan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FanRepository extends CrudRepository<Fan, Long> {

    @Query("SELECT f FROM Fan f WHERE f.username=:username")
    Iterable<Fan> findFanByUsername(@Param("username") String u);

    @Query("SELECT f.userId FROM Fan f where f.username=:username")
    long findFanIdByUsername(@Param("username") String u);

    @Query("SELECT f FROM Fan f where f.username=:username and f.password=:password")
    Iterable<Fan> findFanByCredential(@Param("username") String username, @Param("password") String password);
}
