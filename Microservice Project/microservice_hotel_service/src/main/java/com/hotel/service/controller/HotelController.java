package com.hotel.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.service.entities.Hotel;
import com.hotel.service.service.HotelService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
public class HotelController {

    public final HotelService hotelService;

//    http://localhost:8030/hotel

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(value = "/add-hotel", consumes = "application/json")
    public ResponseEntity<Hotel> addRating(@RequestBody Hotel hotel) {
        Hotel addedHotel = hotelService.addHotel(hotel);
        return ResponseEntity.ok(addedHotel);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')") // if you define internal scope, API will be called internally only via other service.
    @GetMapping(value = "/get-hotel", produces = "application/json")
    public ResponseEntity<Hotel> getHotel(@RequestParam("hotelId") String hotelId) {
        Hotel fetchedHotel = hotelService.getHotel(hotelId);
        return ResponseEntity.ok(fetchedHotel);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping(value = "/get-all-hotels", produces = "application/json")
    public ResponseEntity<List<Hotel>> getHotels() {
        List<Hotel> fetchedHotels = hotelService.getHotels();
        return ResponseEntity.ok(fetchedHotels);
    }
}
