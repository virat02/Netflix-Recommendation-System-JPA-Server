package com.example.app.repositories;

import com.example.app.models.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a where a.username=:username AND a.password=:password")
    Iterable<Admin> findAdminByCredentials(@Param("username") String username, @Param("password") String password);
}
