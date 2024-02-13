package com.hotel.service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotel.service.entities.Hotel;
import com.hotel.service.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel addHotel(Hotel hotel) {
        String randomHotelId = UUID.randomUUID().toString();
        hotel.setHotelId(randomHotelId);
        hotel = hotelRepository.save(hotel);
        return hotel;
    }

    public Hotel getHotel(String hotelId) {
        Hotel hotel = hotelRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find hotel with id: " + hotelId));
        return hotel;
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        if (hotels.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find hotels");
        }
        return hotels;
    }
}
