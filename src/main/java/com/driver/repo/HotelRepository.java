package com.driver.repo;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class HotelRepository {
    private HashMap<String, Hotel> hotelDb;
    private HashMap<Integer, User> userDb;
    private HashMap<String, Booking> bookingDb;

    private HashMap<Integer, List<Booking>> personBookingsDb;

    public HotelRepository() {
        this.hotelDb = new HashMap<>();
        this.userDb = new HashMap<>();
        this.bookingDb = new HashMap<>();
        this.personBookingsDb = new HashMap<>();
    }

    public boolean addHotel(Hotel hotel) {
        if (hotel == null || hotel.getHotelName() == null) return false;
        if (hotelDb.containsKey(hotel.getHotelName())) return false;
        hotelDb.put(hotel.getHotelName(), hotel);
        return true;
    }

    public Integer addUser(User user) {
        userDb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String getHotelNameWithMostFacilities() {
        List<Hotel> possibleHotels = new ArrayList<>();
        boolean flag = false;
        for (Hotel h : hotelDb.values()) {
            if (!h.getFacilities().isEmpty()) {
                flag = true;
                break;
            }
        }
        if (!flag) return "";

        int maxFacility = 1;
        for (Hotel h : hotelDb.values()) {
            if (h.getFacilities().size() > maxFacility) {
                maxFacility = h.getFacilities().size();
            }
        }

        for (Hotel h : hotelDb.values()) {
            if (h.getFacilities().size() == maxFacility) {
                possibleHotels.add(h);
            }
        }
        possibleHotels.sort(Comparator.comparing(Hotel::getHotelName));
        return possibleHotels.get(0).getHotelName();
    }

    public int bookARoomAndGetPrice(Booking booking) {
        String bookingId = UUID.randomUUID().toString();
        int amountToBePaid;

        Hotel hotelToBeBooked = hotelDb.getOrDefault(booking.getHotelName(), null);
        if (hotelToBeBooked == null) return -1;

        if (hotelToBeBooked.getAvailableRooms() < booking.getNoOfRooms()) return -1;

        hotelToBeBooked.setAvailableRooms(hotelToBeBooked.getAvailableRooms() - booking.getNoOfRooms());
        amountToBePaid = booking.getNoOfRooms() * hotelToBeBooked.getPricePerNight();

        booking.setAmountToBePaid(amountToBePaid);

        bookingDb.put(bookingId, booking);

        var personBookings = personBookingsDb.getOrDefault(booking.getBookingAadharCard(), new ArrayList<>());
        personBookings.add(booking);
        personBookingsDb.put(booking.getBookingAadharCard(), personBookings);

        return amountToBePaid;
    }

    public int getBookings(Integer aadharCard) {
        return personBookingsDb.getOrDefault(aadharCard, new ArrayList<>()).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);
        for (Facility f : newFacilities) {
            if (hotel.getFacilities().contains(f)) continue;
            hotel.getFacilities().add(f);
        }
        return hotel;
    }
}
