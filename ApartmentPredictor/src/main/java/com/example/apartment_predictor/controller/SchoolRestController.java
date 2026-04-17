package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.utils.PopulateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/school")
public class SchoolRestController {

    @Autowired
    PopulateDB populateDB;

    @Autowired
    SchoolRepository schoolRepository;

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<School>> getAllSchools() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllSchools executed");
        headers.add("version", "1.0 Api Rest School Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        
        return ResponseEntity.ok().headers(headers).body(schoolRepository.findAll());
    }

    @GetMapping("/getById")
    public ResponseEntity<School> getSchoolById(@RequestParam String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getSchoolById executed");
        headers.add("version", "1.0 Api Rest School Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        
        School school = schoolRepository.findById(id).orElse(null);
        return ResponseEntity.ok().headers(headers).body(school);
    }

    @PostMapping("/create")
    public ResponseEntity<School> saveSchool(@RequestBody School school) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "saveSchool executed");
        headers.add("version", "1.0 Api Rest School Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        
        return ResponseEntity.ok().headers(headers).body(schoolRepository.save(school));
    }

    @GetMapping("/populate")
    public ResponseEntity<String> populateSchools(@RequestParam int qty) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "populateSchools executed");
        headers.add("version", "1.0 Api Rest School Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        
        List<School> schools = populateDB.populateSchools(qty);
        if (schools.size() > 0)
            return ResponseEntity.ok().headers(headers).body("Populated schools: " + schools.size());
        else
            return ResponseEntity.badRequest().headers(headers).body("Failed to populate schools");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSchools() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "deleteAllSchools executed");
        headers.add("version", "1.0 Api Rest School Object");
        headers.add("active", "true");
        headers.add("author", "Albert");
        
        schoolRepository.deleteAll();
        return ResponseEntity.ok().headers(headers).body("All schools deleted");
    }
}
