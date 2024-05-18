package com.example.Hotel.controllers;

import com.example.Hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/get_hotels")
    public String get_hotels(@RequestParam double radius, @RequestParam double lat, @RequestParam double lng) {
       return hotelService.hello_from_service(radius, lat, lng);
    }

}