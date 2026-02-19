package com.codingairbnb.project.airBnbApp.controller;

import com.codingairbnb.project.airBnbApp.dto.HotelInfoDto;
import com.codingairbnb.project.airBnbApp.dto.HotelPriceDto;
import com.codingairbnb.project.airBnbApp.dto.HotelSearchRequest;
import com.codingairbnb.project.airBnbApp.services.HotelService;
import com.codingairbnb.project.airBnbApp.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){

        var page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
