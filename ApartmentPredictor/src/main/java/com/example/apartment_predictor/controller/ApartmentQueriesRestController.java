package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apartment/queries")
public class ApartmentQueriesRestController {

    @Autowired
    private ApartmentRepository apartmentRepository;

    // 1. Find apartments by price range
    @GetMapping("/price-range")
    public List<Apartment> findByPriceRange(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
        return apartmentRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // 2. Find apartments by number of bedrooms
    @GetMapping("/bedrooms")
    public List<Apartment> findByBedrooms(@RequestParam Integer bedrooms) {
        return apartmentRepository.findByBedrooms(bedrooms);
    }

    // 2.1 Find apartments by number of bathrooms and PriceBetween Asc
    @GetMapping("/bedrooms-and-price-between-asc")
    public List<Apartment> findByBedroomsAndPriceBetween(@RequestParam Integer bedrooms, @RequestParam Long minPrice, @RequestParam Long maxPrice) {
        return apartmentRepository.findByBedroomsAndPriceBetween(bedrooms, minPrice, maxPrice);
    }
}
