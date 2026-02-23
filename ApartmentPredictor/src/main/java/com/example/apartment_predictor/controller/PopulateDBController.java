package com.example.apartment_predictor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;
import com.example.apartment_predictor.utils.PopulateDB;

@RestController
@RequestMapping("api/populate")

public class PopulateDBController {

    @Autowired
    private PopulateDB populateDB;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    SchoolRepository schoolRepository;

   
    @GetMapping("/populateAll")
    public ResponseEntity<String> populateAll(@RequestParam int qty) {
        int qtyPopulateAll = populateDB.populateAll(qty);
        if (qtyPopulateAll > 0)
            return ResponseEntity.ok("PopulateAll Objects: " + qtyPopulateAll);
        else
            return ResponseEntity.badRequest().body("Failed to populateAll Objects");
    }


    @GetMapping("/populateApartments")
    public ResponseEntity<String> populateApartments(@RequestParam int qty) {
        int qtyApartmentsCreated = populateDB.populatePlainApartments(qty).size();
        if (qtyApartmentsCreated > 0)
            return ResponseEntity.ok("Populated apartments: " + qtyApartmentsCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate apartments");
    }

     @GetMapping("/populateSchools")
    public ResponseEntity<String> populateSchools(@RequestParam int qty) {
        int qtySchoolsCreated = populateDB.populateSchools(qty).size();
        if (qtySchoolsCreated > 0)
            return ResponseEntity.ok("Populated schools: " + qtySchoolsCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate schools");
    }

    @PutMapping("/assignSchoolsToApartment")
    public ResponseEntity<Apartment> assignSchoolsToApartment(
            @RequestParam String apartmentId,
            @RequestParam List<String> schoolIds
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "assignSchoolsToApartment executed");
        headers.add("version", "1.0 Api Rest Apartment Object");
        headers.add("active", "true");
        headers.add("author", "Albert");

        Apartment apartment = apartmentService.findApartmentById(apartmentId);
        if (apartment == null) {
            headers.add("Status", "assignSchoolsToApartment failed: apartment not found");
            return ResponseEntity.
                    badRequest().headers(headers).
                    body(null);
        }

        if (schoolIds == null || schoolIds.isEmpty()) {
            headers.add("Status", "assignSchoolsToApartment failed: schoolIds is null or empty");
            apartment.setSchools(new ArrayList<>());
            return ResponseEntity.badRequest().headers(headers).body(null);
        }

        Iterable<School> found = schoolRepository.findAllById(schoolIds);
        List<School> schoolsFound = StreamSupport.stream(found.spliterator(), false)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (schoolsFound.size() != schoolIds.size()) {
            headers.add("Status", "assignSchoolsToApartment failed: schools not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }

        apartment.addSchools(schoolsFound);
        headers.add("Status", "assignSchoolsToApartment success");
        Apartment apartmentSaved = apartmentService.updateApartment(apartment);
        return ResponseEntity.ok().headers(headers).body(apartmentSaved);
    }

    @GetMapping("/populatePlainReviews")
    public ResponseEntity<String> populatePlainReviews(@RequestParam int qty) {
        int qtyReviewsCreated = populateDB.createPlainReviews(qty).size();
        if (qtyReviewsCreated > 0)
            return ResponseEntity.ok("Populated Reviews: " + qtyReviewsCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate Reviews");
    }

    @GetMapping("/populateReviewers")
    public ResponseEntity<String> populateReviewers(@RequestParam int qty) {
        int qtyReviewersCreated = populateDB.populateReviewers(qty).size();
        if (qtyReviewersCreated > 0)
            return ResponseEntity.ok("Populated Reviewers: " + qtyReviewersCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate Reviewers");
    }
}

