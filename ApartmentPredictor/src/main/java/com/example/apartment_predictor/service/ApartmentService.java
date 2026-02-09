package com.example.apartment_predictor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.github.javafaker.Faker;

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

    public String assignSchoolsToApartment(String getApartmentId, List<School> schoolList){
        
        return ("ok");
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


    public void createRandomApartments(Integer quantity) {

    Faker faker = new Faker(Locale.forLanguageTag("es"));
    List<Apartment> apartments = new ArrayList<>();

    for (int i = 0; i < quantity; i++) {

        Apartment apartment = new Apartment();

        //apartment.setPrice(faker.number().numberBetween(3000, 300000));
        apartment.setArea(faker.number().numberBetween(30, 250));
        apartment.setBedrooms(faker.number().numberBetween(1, 6));
        apartment.setBathrooms(faker.number().numberBetween(1, 4));
        apartment.setStories(faker.number().numberBetween(1, 3));

        //apartment.setMainroad(faker.address().streetName());
        //apartment.setGuestroom(faker.expression(null));
        
        int resultado = (int)(Math.random() * 2);
        apartment.setBasement(String.valueOf(resultado));
        apartment.setAirconditioning(Math.random() < 0.5 ? "no" : "yes");
        apartment.setParking(faker.number().numberBetween(0, 4));

        //apartment.setHotwaterheating(faker.bool().bool());
        //apartment.setPrefarea(faker.bool().bool());

        apartment.setFurnishingstatus(
                faker.options().option("furnished", "semi-furnished", "unfurnished")
        );

        apartments.add(apartment);
    }

    apartmentRepository.saveAll(apartments);
}




}
