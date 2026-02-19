package com.codingairbnb.project.airBnbApp.repository;

import com.codingairbnb.project.airBnbApp.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
