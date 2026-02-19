package com.codingairbnb.project.airBnbApp.dto;

import com.codingairbnb.project.airBnbApp.entities.ContactInfo;
import lombok.Data;

@Data
public class HotelDto {

    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private ContactInfo contactInfo;
    private Boolean active;

}
