package com.codingairbnb.project.airBnbApp.controller;

import com.codingairbnb.project.airBnbApp.dto.BookingDto;
import com.codingairbnb.project.airBnbApp.dto.ProfileUpdateRequestDto;
import com.codingairbnb.project.airBnbApp.dto.UserDto;
import com.codingairbnb.project.airBnbApp.services.BookingService;
import com.codingairbnb.project.airBnbApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto){
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getMyProfile(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

}
