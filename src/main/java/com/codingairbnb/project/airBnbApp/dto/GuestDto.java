package com.codingairbnb.project.airBnbApp.dto;

import com.codingairbnb.project.airBnbApp.entities.User;
import com.codingairbnb.project.airBnbApp.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;

    private User user;

    private String name;

    private Gender gender;

    private Integer age;
}
