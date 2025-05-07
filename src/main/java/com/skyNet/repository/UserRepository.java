package com.skyNet.repository;

import com.skyNet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    @Query(value = "Select * from users where email = :email", nativeQuery = true)
    User findUserByEmail(@Param("email") String email);

    @Query(value = "Select u from User u")
    List<User> findAllBy();

}

