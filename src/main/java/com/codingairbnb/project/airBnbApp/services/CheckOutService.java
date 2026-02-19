package com.codingairbnb.project.airBnbApp.services;

import com.codingairbnb.project.airBnbApp.entities.Booking;


public interface CheckOutService {

    String getCheckOutSession(Booking booking, String successUrl, String failureUrl);
}
