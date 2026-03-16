package com.example.apartment_predictor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;
import com.example.apartment_predictor.utils.PopulateDB;

@RestController
@RequestMapping("api/v1/apartment")
@CrossOrigin(origins = "http://localhost:5173/")
public class ApartmentRestController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    PopulateDB populateDB;

    @Autowired
    SchoolRepository schoolRepository;

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

    @GetMapping("/populate")
    public ResponseEntity<String> populateApartments(@RequestParam int qty) {
        // CALL to PopulateDB.populateApartments() method
        // to populate the database with random apartments
        int qtyApartmentsCreated = populateDB.populatePlainApartments(qty).size();
        if (qtyApartmentsCreated > 0)
            return ResponseEntity.ok("Populated apartments: " + qtyApartmentsCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate apartments");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Apartment>> getApartmentsPaginated(@RequestParam int pageNo) {
        final int PAGE_SIZE = 5;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getApartmentsPaginated executed");
        headers.add("version", "1.0 Api Rest Apartment Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        headers.add("pageSize", String.valueOf(PAGE_SIZE));
        headers.add("pageNo", String.valueOf(pageNo));


        Page<Apartment> apartments = apartmentService.findPaginated(pageNo, PAGE_SIZE);
        headers.add("totalObjects", String.valueOf(apartments.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(apartments);
    }


}
