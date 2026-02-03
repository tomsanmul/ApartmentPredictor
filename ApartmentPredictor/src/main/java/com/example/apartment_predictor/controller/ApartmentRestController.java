package com.example.apartment_predictor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;

@CrossOrigin(origins = "http://localhost:5173")
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

    @PostMapping("/create_apartments")
        public String createRandomApartments(@RequestParam Integer quantity) {
        apartmentService.createRandomApartments(quantity);
        return (quantity + " apartments created!");
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
