package com.codingairbnb.project.airBnbApp.controller;

import com.codingairbnb.project.airBnbApp.dto.BookingDto;
import com.codingairbnb.project.airBnbApp.dto.BookingRequest;
import com.codingairbnb.project.airBnbApp.dto.GuestDto;
import com.codingairbnb.project.airBnbApp.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initialseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/payments")
    public ResponseEntity<Map<String, String>> initiatePayment(@PathVariable Long bookingId){
        String sessionUrl = bookingService.initiatePayments(bookingId);
        return ResponseEntity.ok(Map.of("sessionUrl", sessionUrl));
    }

    @PostMapping("/{bookingId}/addGuests")
    ResponseEntity<Void> cancelBooking(@PathVariable Long booingId){
        String sessionUrl = bookingService.cancelBooking(booingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{booingId}/status")
    ResponseEntity<Map<String, String>> getBookingStatus(@PathVariable Long bookingId){
        return ResponseEntity.ok(Map.of("status", bookingService.getBookingStatus(bookingId)));
    }
}
