package com.codingairbnb.project.airBnbApp.dto;

import com.codingairbnb.project.airBnbApp.entities.Hotel;
import com.codingairbnb.project.airBnbApp.entities.Room;
import com.codingairbnb.project.airBnbApp.entities.User;
import com.codingairbnb.project.airBnbApp.entities.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@Data
public class BookingDto {

    private Long id;

    private User user;

    private Integer roomCount;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BookingStatus bookingStatus;

    private Set<GuestDto> guests;

    private BigDecimal amount;
}
