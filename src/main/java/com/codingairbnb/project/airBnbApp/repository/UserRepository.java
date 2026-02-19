package com.codingairbnb.project.airBnbApp.repository;

import com.codingairbnb.project.airBnbApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
