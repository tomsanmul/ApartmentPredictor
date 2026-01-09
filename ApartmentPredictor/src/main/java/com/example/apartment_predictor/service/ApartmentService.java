package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Apartment updateApartmentById(String id, Apartment apartment) {
        if (id == null || apartment == null) {
            return null;
        }

        Optional<Apartment> existingOpt = apartmentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Apartment existing = existingOpt.get();

        if (apartment.getPrice() != null) existing.setPrice(apartment.getPrice());
        if (apartment.getArea() != null) existing.setArea(apartment.getArea());
        if (apartment.getBedrooms() != null) existing.setBedrooms(apartment.getBedrooms());
        if (apartment.getBathrooms() != null) existing.setBathrooms(apartment.getBathrooms());
        if (apartment.getStories() != null) existing.setStories(apartment.getStories());
        if (apartment.getMainroad() != null) existing.setMainroad(apartment.getMainroad());
        if (apartment.getGuestroom() != null) existing.setGuestroom(apartment.getGuestroom());
        if (apartment.getBasement() != null) existing.setBasement(apartment.getBasement());
        if (apartment.getHotwaterheating() != null) existing.setHotwaterheating(apartment.getHotwaterheating());
        if (apartment.getAirconditioning() != null) existing.setAirconditioning(apartment.getAirconditioning());
        if (apartment.getParking() != null) existing.setParking(apartment.getParking());
        if (apartment.getPrefarea() != null) existing.setPrefarea(apartment.getPrefarea());
        if (apartment.getFurnishingstatus() != null) existing.setFurnishingstatus(apartment.getFurnishingstatus());

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




}
