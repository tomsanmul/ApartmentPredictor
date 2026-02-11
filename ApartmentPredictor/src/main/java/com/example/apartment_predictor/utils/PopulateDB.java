package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PopulateDB {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    SchoolRepository schoolRepository;

    //todo: all methods MUST return the objects created

    public int populateAll(int qty) {
        return 0;
    }

    // populate apartments and schools

    public int populateSchools(int qty) {
        int qtySchoolsCreated = 0;
        if (qty <= 0) return 0;

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] schoolTypes = {"public", "private", "religious"};
        String[] locations = {"Downtown", "Uptown", "Suburbs", "East Side", "West Side"};
        String[] namePrefixes = {"Green", "Oak", "River", "Hill", "Sunrise", "Cedar", "Lakeside"};
        String[] nameSuffixes = {"Academy", "School", "Institute", "High School", "College"};

        for (int i = 0; i < qty; i++) {
            String type = schoolTypes[rnd.nextInt(schoolTypes.length)];
            String location = locations[rnd.nextInt(locations.length)];
            int rating = rnd.nextInt(1, 6);
            boolean isPublic = "public".equals(type);

            String name = namePrefixes[rnd.nextInt(namePrefixes.length)] + " " + nameSuffixes[rnd.nextInt(nameSuffixes.length)];

            School school = new School(name, type, location, rating, isPublic);
            schoolRepository.save(school);

            School schoolById = schoolRepository.findById(school.getId()).orElse(null);
            if (schoolById != null) {
                qtySchoolsCreated++;
                System.out.println(
                        "School #" + qtySchoolsCreated +
                                "/" + qty + " created populateDB: " + schoolById);
            }
        }

        return qtySchoolsCreated;
    }

    public int populateApartments(int qty) {
        int qtyApartmetnsCreated = 0;
        if (qty <= 0) return 0;

        //Faker faker = new Faker(new Locale("en-US"));
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] furnishingOptions = {"furnished", "semi-furnished", "unfurnished"};

        for (int i = 0; i < qty; i++) {
            Long price = rnd.nextLong(30_000, 600_001);          // adjust range if you want
            Integer area = rnd.nextInt(300, 5001);              // adjust range if you want
            Integer bedrooms = rnd.nextInt(1, 7);
            Integer bathrooms = rnd.nextInt(1, 5);
            Integer stories = rnd.nextInt(1, 5);

            String mainroad = rnd.nextBoolean() ? "yes" : "no";
            String guestroom = rnd.nextBoolean() ? "yes" : "no";
            String basement = rnd.nextBoolean() ? "yes" : "no";
            String hotwaterheating = rnd.nextBoolean() ? "yes" : "no";
            String airconditioning = rnd.nextBoolean() ? "yes" : "no";
            Integer parking = rnd.nextInt(0, 4);
            String prefarea = rnd.nextBoolean() ? "yes" : "no";

            String furnishingstatus = furnishingOptions[rnd.nextInt(furnishingOptions.length)];

            Apartment apartment = new Apartment(
                    price,
                    area,
                    bedrooms,
                    bathrooms,
                    stories,
                    mainroad,
                    guestroom,
                    basement,
                    hotwaterheating,
                    airconditioning,
                    parking,
                    prefarea,
                    furnishingstatus
            );

            apartmentService.createApartment(apartment);

            Apartment apartmentById = apartmentService.findApartmentById(apartment.getId());
            if (apartmentById != null) {
                qtyApartmetnsCreated++;
                System.out.println(
                        "Apartment #" + qtyApartmetnsCreated +
                         "/" + qty + " created populateDB: " + apartmentById);
            }

        }
        return qtyApartmetnsCreated;
    }

   public int assignSchoolsToApartments(List<Apartment> apartments, List<School> schools) {
       return 0;
   }



    public int populateReviews(int qty) {
        return 0;
    }

    public int populateReviewers(int qty) {
        return 0;
    }

    public int populateOwners(int qty) {
        return 0;
    }

    public int populatePropertyContracts(int qty) {
        return 0;
    }
}
