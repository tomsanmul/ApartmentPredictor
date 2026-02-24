package com.example.apartment_predictor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.utils.PopulateDB;

@RestController
@RequestMapping("api/populate")

public class PopulateDBController {

    @Autowired
    private PopulateDB populateDB;
    

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

     @GetMapping("/populateOwners")
    public ResponseEntity<String> populateOwners(@RequestParam int qty) {
        int qtyeOwnersCreated = populateDB.populateOwners(qty).size();
        if (qtyeOwnersCreated > 0)
            return ResponseEntity.ok("Populated Reviewers: " + qtyeOwnersCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate Reviewers");
    }


 
}

