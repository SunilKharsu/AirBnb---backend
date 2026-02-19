package com.codingairbnb.project.airBnbApp.util;

import com.codingairbnb.project.airBnbApp.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtil {

    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
