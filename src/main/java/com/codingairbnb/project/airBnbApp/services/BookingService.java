package com.codingairbnb.project.airBnbApp.services;

import com.codingairbnb.project.airBnbApp.dto.BookingDto;
import com.codingairbnb.project.airBnbApp.dto.BookingRequest;
import com.codingairbnb.project.airBnbApp.dto.GuestDto;
import com.codingairbnb.project.airBnbApp.dto.HotelReportDto;
import com.stripe.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingDto initialseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    String cancelBooking(Long booingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingByHotelId(Long bookingId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
