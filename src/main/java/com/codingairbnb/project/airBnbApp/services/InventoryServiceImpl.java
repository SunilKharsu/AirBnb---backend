package com.codingairbnb.project.airBnbApp.services;

import com.codingairbnb.project.airBnbApp.dto.HotelPriceDto;
import com.codingairbnb.project.airBnbApp.dto.HotelSearchRequest;
import com.codingairbnb.project.airBnbApp.dto.InventoryDto;
import com.codingairbnb.project.airBnbApp.dto.UpdateInventoryRequestDto;
import com.codingairbnb.project.airBnbApp.entities.Inventory;
import com.codingairbnb.project.airBnbApp.entities.Room;
import com.codingairbnb.project.airBnbApp.entities.User;
import com.codingairbnb.project.airBnbApp.exception.ResourceNotFoundException;
import com.codingairbnb.project.airBnbApp.repository.HotelMinPriceRepository;
import com.codingairbnb.project.airBnbApp.repository.InventoryRepository;
import com.codingairbnb.project.airBnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.codingairbnb.project.airBnbApp.util.AppUtil.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    private final RoomRepository roomRepository;

    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final ModelMapper modelMapper;


    @Override
    public void initializeRoomForAYear(Room room) {

        LocalDate today = LocalDate.now();

        LocalDate endDate = today.plusYears(1);

        for(; today.isAfter(endDate); today = today.plusDays(1)){

            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("Deleting the inventories of room with Id: {}", room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {

        log.info("Searching hotels for city {}, from {} to {}", hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate());

        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());

        long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;

        //business logic - 90days
        Page<HotelPriceDto> hotelPage = hotelMinPriceRepository.findHotelWithAvailable(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(),
                dateCount, pageable);

        return hotelPage;
    }

    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {
        log.info("Getting all inventory by room for room with Id {}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with Id "+roomId));

        User user = getCurrentUser();
        if(!user.equals(room.getHotel().getOwner())) try {
            throw new AccessDeniedException("You ar ethe not owner of this hotel" + room.getHotel().getId());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        return inventoryRepository.findByRoomOrderByDate(room)
                .stream()
                .map((element) -> modelMapper.map(element, InventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) {
        log.info("Updating all inventory by room for room with id: {} between date range: {} - {}", roomId,
                updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: "+roomId));

        User user = getCurrentUser();

        if(!user.equals(room.getHotel().getOwner())) try {
            throw new AccessDeniedException("You are not owner of this room with Id "+roomId);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        inventoryRepository.getInventoryAndLockBeforeUpdate(roomId, updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate());

        inventoryRepository.updateInventory(roomId, updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate(), updateInventoryRequestDto.getClosed(),
                updateInventoryRequestDto.getSurgeFactor());
    }
}
