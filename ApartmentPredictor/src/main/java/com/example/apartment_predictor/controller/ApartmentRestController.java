package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/apartment")
public class ApartmentRestController {

    @Autowired
    ApartmentService apartmentService;

    @GetMapping("/getAll")
    public Iterable<Apartment> getAllApartments(){
        return apartmentService.findAll();
    }

    @GetMapping("/getById")
    public Apartment getApartmentById(@RequestParam String id){
        return apartmentService.findApartmentById(id);
    }

    @PostMapping("/create")
    public Apartment createApartment(@RequestBody Apartment apartment){
        return apartmentService.createApartment(apartment);
    }

    @DeleteMapping("/deleteById")
    public void deleteApartmentById(@RequestParam String id){
        apartmentService.deleteApartment(id);
    }

}
