package com.example.apartment_predictor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.apartment_predictor.model.Apartment;

public interface ApartmentRepository extends CrudRepository<Apartment, String>, PagingAndSortingRepository<Apartment, String>, JpaSpecificationExecutor<Apartment> {

    // 1. Find apartments by price range
    List<Apartment> findByPriceBetween(Long minPrice, Long maxPrice);

    // 2. Find apartments by number of bedrooms
    List<Apartment> findByBedrooms(Integer bedrooms);

    // 2.1 Find apartments by number of bathrooms and PriceBetween Asc
    List<Apartment> findByBedroomsAndPriceBetween(Integer bedrooms, Long minPrice, Long maxPrice);

    // 3. Find apartments by area greater than
    List<Apartment> findByAreaGreaterThan(Integer minArea);

    // 4. Find apartments by furnishing status
    List<Apartment> findByFurnishingstatus(String furnishingstatus);

    // 5. Find apartments with air conditioning
    List<Apartment> findByAirconditioning(String airconditioning);

    // 6. Find apartments by number of bathrooms and bedrooms
    List<Apartment> findByBathroomsAndBedrooms(Integer bathrooms, Integer bedrooms);

    // 7. Find apartments with parking spaces
    List<Apartment> findByParkingGreaterThan(Integer minParking);

    // 8. Find apartments in preferred area
    List<Apartment> findByPrefarea(String prefarea);

    // 9. Find apartments by number of stories
    List<Apartment> findByStories(Integer stories);

    // 10. Find apartments by main road access and guest room availability
    List<Apartment> findByMainroadAndGuestroom(String mainroad, String guestroom);

    // 11. Find the number apartments that have air conditioning and at least 2 parking spots
    long countByAirconditioningAndParkingGreaterThanEqual(String airconditioning, Integer parking);

    // 12. Find apartments by price range and have Basement
    boolean existsByPriceBetweenAndBasement(Long minPrice, Long maxPrice, String basement);

     // 13. finds the top 10 apartments ordered by averageRating descending, but only those with basement
    List<Apartment> findTop10ByBasementAndReviewCountGreaterThanEqualOrderByAverageRatingDesc(Integer reviewCount);

}
