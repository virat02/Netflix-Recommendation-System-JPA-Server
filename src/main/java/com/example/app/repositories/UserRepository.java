package com.example.app.repositories;

import com.example.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username=:username")
    Iterable<User> findUserByUsername(@Param("username") String u);

    @Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
    Optional<User> findUserByCredentials(@Param("username") String username, @Param("password") String password);
}
