package com.example.apartment_predictor.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;

@RestController
@RequestMapping("api/v1/apartment")
@CrossOrigin(origins = "http://localhost:5173/")
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


    //Derived Queries Lab

    //Exercise 1 – Basic Count
    //Implement a method in ApartmentRepository that returns the number of apartments that have air conditioning and at least 2 parking spots.
    //Use a derived query method with countBy....
    //Call it from a service/controller and display: "X apartments match your criteria (with AC and ≥2 parking)".
    //Bonus: Also add a count of apartments without balcony.
    @GetMapping("/count-apartments")
    public String countApartments() {
        long count = apartmentRepository.countByAirconditioningAndParkingGreaterThanEqual("yes", 2);
        return count + " apartamentos coinciden con tus criterios (con aire acondicionado y ≥2 plazas de aparcamiento).";
    }

    //Exercise 2 – Exists Check for Quick Validation
    //Create a derived query method that checks if there exists at least one apartment in a given price range (minPrice ≤ price ≤ maxPrice) that has a balcony.
    //In the controller: if the method returns false, show a friendly message: "No apartments with balcony in your budget range — try increasing max price or removing balcony filter".
   @GetMapping("/exists-price-range-with-basement")
   public String checkApartmentsInPriceRange(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
        boolean exists = apartmentRepository.existsByPriceBetweenAndBasement(minPrice, maxPrice, "yes");
        if (exists) {
            return "There is at least one apartment in your budget with basement";
        } else {
            return "No apartments with basement in your budget range — try increasing max price or removing basement filter";
        }
    }
    
    //Exercise 3 – Ordered List + Top Results
    //Write a derived query method that finds the top 10 apartments ordered by averageRating descending, but only those with balcony = true and reviewCount ≥ 5.
    //Use OrderByAverageRatingDesc + Top10 + conditions.
    //Display them in the UI as "Highly rated apartments with balcony (sorted best first)".
    
    @GetMapping("/highly-rated-with-basement")
        public List<Apartment> getHighlyRatedWithBasement() {
            return null;
        //return apartmentRepository.findTop10ByBasementAndReviewCountGreaterThanEqualOrderByAverageRatingDesc("yes", 5);
}


}
