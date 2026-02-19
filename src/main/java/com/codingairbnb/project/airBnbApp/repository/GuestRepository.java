package com.codingairbnb.project.airBnbApp.repository;

import com.codingairbnb.project.airBnbApp.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
