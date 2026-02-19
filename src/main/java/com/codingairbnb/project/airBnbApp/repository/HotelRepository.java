package com.codingairbnb.project.airBnbApp.repository;

import com.codingairbnb.project.airBnbApp.entities.Hotel;
import com.codingairbnb.project.airBnbApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByOwner(User user);
}
