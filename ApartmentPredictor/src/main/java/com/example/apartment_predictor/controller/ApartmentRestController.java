package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/apartment")
public class ApartmentRestController {

    @Autowired
    ApartmentService apartmentService;

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Apartment>> getAllApartments(){

       HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllApartments executed");
        headers.add("version", "1.0 Api Rest Apartment Object");
        headers.add("active", "true");
        headers.add("author", "Albert");

        return ResponseEntity.ok().headers(headers).body(apartmentService.findAll());
        //return apartmentService.findAll();
    }

    @GetMapping("/getById")
    public Apartment getApartmentById(@RequestParam String id){
        return apartmentService.findApartmentById(id);
    }

    @PostMapping("/create")
    public Apartment createApartment(@RequestBody Apartment apartment){
        //apartment.setId(UUID.randomUUID().toString());
        return apartmentService.createApartment(apartment);
    }

    @PostMapping("/update")
    public Apartment updateApartment(@RequestBody Apartment apartment){
        return apartmentService.updateApartment(apartment);
    }

    @DeleteMapping("/deleteById")
    public void deleteApartmentById(@RequestParam String id){
        apartmentService.deleteApartment(id);
    }

    @PutMapping("/updateById")
    public Apartment updateApartmentById(@RequestParam String id, @RequestBody Apartment apartment){
        return apartmentService.updateApartmentById(id, apartment);
    }

}
