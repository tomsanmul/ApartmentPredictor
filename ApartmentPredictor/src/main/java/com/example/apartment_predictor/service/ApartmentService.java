package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    // implement CRUD operations: read
    public Iterable<Apartment> findAll() {



        return apartmentRepository.findAll();
    }


    public void createApartment(){}

    public void updateApartment (){}

    public void deleteApartment (){}

    public Apartment findApartmentById (String id){

        Optional<Apartment> found = apartmentRepository.findById(id);
        if ( found.isPresent()) return found.get() ;
        else return null;


    }

    public void findApartmentByPrice (){}




}
