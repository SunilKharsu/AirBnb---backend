package com.codingairbnb.project.airBnbApp.services;

import com.codingairbnb.project.airBnbApp.dto.ProfileUpdateRequestDto;
import com.codingairbnb.project.airBnbApp.dto.UserDto;
import com.codingairbnb.project.airBnbApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
