package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.utils.PopulateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/school")
public class SchoolRestController {

    @Autowired
    PopulateDB populateDB;

    @GetMapping("/populate")
    public ResponseEntity<String> populateSchools(@RequestParam int qty) {
        int qtySchoolsCreated = populateDB.populateSchools(qty);
        if (qtySchoolsCreated > 0)
            return ResponseEntity.ok("Populated schools: " + qtySchoolsCreated);
        else
            return ResponseEntity.badRequest().body("Failed to populate schools");
    }
}
