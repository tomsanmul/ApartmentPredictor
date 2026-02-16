package com.example.apartment_predictor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PopulateDBController {

    @GetMapping("/populateDB/{qty}")
    public void populateDB(int qty) {

        // call to populateAll orchestrator at PopulateDB.java
        // src/main/java/com/example/apartment_predictor/utils/PopulateDB.java

    }
}
