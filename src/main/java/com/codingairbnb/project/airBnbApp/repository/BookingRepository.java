package com.codingairbnb.project.airBnbApp.repository;

import com.codingairbnb.project.airBnbApp.entities.Booking;
import com.codingairbnb.project.airBnbApp.entities.Hotel;
import com.codingairbnb.project.airBnbApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPaymentSessionId(String sessionId);

    List<Booking> findByHotel(Hotel hotel);

    List<Booking> findByHotelAndCreatedAtBetween(Hotel hotel, LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByUser(User user);
}
