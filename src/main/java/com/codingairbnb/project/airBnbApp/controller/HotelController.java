package com.codingairbnb.project.airBnbApp.controller;

import com.codingairbnb.project.airBnbApp.dto.BookingDto;
import com.codingairbnb.project.airBnbApp.dto.HotelDto;
import com.codingairbnb.project.airBnbApp.dto.HotelReportDto;
import com.codingairbnb.project.airBnbApp.entities.Hotel;
import com.codingairbnb.project.airBnbApp.services.BookingService;
import com.codingairbnb.project.airBnbApp.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;
    private final BookingService bookingService;

    @PostMapping
    ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){

        //log.info("Attempting to create a new hotel with name: {}"+ hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);


    }

    @GetMapping("/{hotelId}")
    ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        HotelDto hotelDto = hotelService.getHotelById(hotelId);

        return  ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto){
        HotelDto hotelDto1 = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotelDto1);
    }

    @DeleteMapping("/{hotelId}")
    ResponseEntity<Boolean> deleteHotelById(@PathVariable Long hotelId){
        Boolean hotelDto = hotelService.deleteHotelById(hotelId);
        return  ResponseEntity.ok(hotelDto);
    }

    @PatchMapping("/{hotelId}/activate")
    ResponseEntity<Void> activateHotel(@PathVariable Long hotelId){
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingByHotelId(@PathVariable Long hotelId){
        return ResponseEntity.ok(bookingService.getAllBookingByHotelId(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    public ResponseEntity<HotelReportDto> getHotelReport(@PathVariable Long hotelId,
                                                         @RequestParam(required = false) LocalDate startDate,
                                                         @RequestParam(required = false) LocalDate endDate
                                                         ){
        if(startDate == null) startDate = LocalDate.now().minusMonths(1);
        if(endDate == null) endDate = LocalDate.now();

        return ResponseEntity.ok(bookingService.getHotelReport(hotelId, startDate, endDate));
    }


}
