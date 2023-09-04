package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    HotelRepository hotelRepository = new HotelRepository();

    public boolean addHotel(Hotel hotel) {
        return hotelRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelRepository.addUser(user);
    }

    public String getHotelNameWithMostFacilities() {
        return hotelRepository.getHotelNameWithMostFacilities();
    }

    public int bookARoomAndGetPrice(Booking booking) {
        return hotelRepository.bookARoomAndGetPrice(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelRepository.updateFacilities(newFacilities, hotelName);
    }
}
