package com.codingairbnb.project.airBnbApp.controller;

import com.codingairbnb.project.airBnbApp.dto.HotelDto;
import com.codingairbnb.project.airBnbApp.dto.RoomDto;
import com.codingairbnb.project.airBnbApp.exception.ResourceNotFoundException;
import com.codingairbnb.project.airBnbApp.repository.HotelRepository;
import com.codingairbnb.project.airBnbApp.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService roomService;
    private final HotelRepository hotelRepository;

    @PostMapping
    ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                          @RequestBody RoomDto room){
        RoomDto roomDto = roomService.createNewRoom(hotelId, room);

        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    ResponseEntity<RoomDto> updateRoomById(@PathVariable Long hotelId, @PathVariable Long roomId,
                                           @RequestBody RoomDto roomDto
    ){
        return ResponseEntity.ok(roomService.updateRoomById(hotelId, roomId, roomDto));
    }
}
