package com.codingairbnb.project.airBnbApp.dto;

import com.codingairbnb.project.airBnbApp.entities.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {

    private String name;
    private LocalDate birthOfDate;
    private Gender gender;
}
