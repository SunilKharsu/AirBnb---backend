package com.codingairbnb.project.airBnbApp.services;

import com.codingairbnb.project.airBnbApp.dto.HotelPriceDto;
import com.codingairbnb.project.airBnbApp.dto.HotelSearchRequest;
import com.codingairbnb.project.airBnbApp.dto.InventoryDto;
import com.codingairbnb.project.airBnbApp.dto.UpdateInventoryRequestDto;
import com.codingairbnb.project.airBnbApp.entities.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
