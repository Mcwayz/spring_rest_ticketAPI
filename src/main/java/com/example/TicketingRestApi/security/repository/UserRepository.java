package com.example.TicketingRestApi.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TicketingRestApi.security.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //find by username 
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    //find by first name and last name 
    User findByFirstnameAndLastname(String firstname, String lastname);
}
