package com.example.apartment_predictor.service;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Locale;
import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    public Iterable<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public Apartment createApartment(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment (Apartment apartment){

       return apartmentRepository.save(apartment);
    }

    public Apartment updateApartmentById (String id, Apartment apartment){
        if (id == null || apartment == null) {
            return null;
        }

        Optional<Apartment> existingOpt = apartmentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Apartment existing = existingOpt.get();
        existing.setPrice(apartment.getPrice());
        existing.setArea(apartment.getArea());
        existing.setBedrooms(apartment.getBedrooms());
        existing.setBathrooms(apartment.getBathrooms());
        existing.setStories(apartment.getStories());
        existing.setMainroad(apartment.getMainroad());
        existing.setGuestroom(apartment.getGuestroom());
        existing.setBasement(apartment.getBasement());
        existing.setHotwaterheating(apartment.getHotwaterheating());
        existing.setAirconditioning(apartment.getAirconditioning());
        existing.setParking(apartment.getParking());
        existing.setPrefarea(apartment.getPrefarea());
        existing.setFurnishingstatus(apartment.getFurnishingstatus());

        if (apartment.getReviews() != null) {
            existing.setReviews(apartment.getReviews());
            for (Review review : existing.getReviews()) {
                if (review != null) {
                    review.setApartment(existing);
                }
            }
        }

        return apartmentRepository.save(existing);
    }

    public void deleteApartment (String id){
        apartmentRepository.deleteById(id);
    }

    public Apartment findApartmentById (String id){
        Optional<Apartment> found = apartmentRepository.findById(id);
        if ( found.isPresent()) return found.get() ;
        else return null;

    }

    public void findApartmentByPrice (){}


    public void createRandomApartments(Integer amount) {

    Faker faker = new Faker(new Locale("en"));
    List<Apartment> apartments = new ArrayList<>();

    for (int i = 0; i < amount; i++) {

        Apartment apartment = new Apartment();

        //apartment.setPrice(faker.number().numberBetween(3000, 300000));
        apartment.setArea(faker.number().numberBetween(30, 250));
        apartment.setBedrooms(faker.number().numberBetween(1, 6));
        apartment.setBathrooms(faker.number().numberBetween(1, 4));
        apartment.setStories(faker.number().numberBetween(1, 3));

        //apartment.setMainroad(faker.bool().bool());
        //apartment.setGuestroom(faker.bool().bool());
        //apartment.setBasement(faker.bool().bool());
        //apartment.setHotwaterheating(faker.bool().bool());
        //apartment.setAirconditioning(faker.bool().bool());

        apartment.setParking(faker.number().numberBetween(0, 4));
        //apartment.setPrefarea(faker.bool().bool());

        apartment.setFurnishingstatus(
                faker.options().option("furnished", "semi-furnished", "unfurnished")
        );

        apartments.add(apartment);
    }

    apartmentRepository.saveAll(apartments);
}




}
