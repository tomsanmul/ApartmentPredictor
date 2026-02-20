package com.example.apartment_predictor.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.OwnerRepository;
import com.example.apartment_predictor.repository.PropertyContractRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import com.example.apartment_predictor.repository.ReviewerRepository;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;

@Component public class PopulateDBMARC {

    @Autowired ApartmentService apartmentService; @Autowired SchoolRepository schoolRepository; @Autowired ReviewerRepository reviewerRepository; @Autowired ReviewRepository reviewRepository;  @Autowired OwnerRepository ownerRepository;@Autowired PropertyContractRepository contractRepository;

    @Transactional
    public int populateAll(int qty) {
        System.out.println(">>> Iniciando orquestador de población...");
        List<Apartment> apartments = populatePlainApartments(qty);
        List<School> schools = populateSchools(qty);
        assignSchoolsToApartments(apartments, schools);
        List<Reviewer> reviewers = populateReviewers(Math.max(1, qty / 2));
        assignReviewsToApartments(apartments, reviewers);
        List<Owner> owners = populateOwners(Math.max(1, qty / 3));
        List<PropertyContract> contracts = populatePropertyContracts(apartments, owners);
        System.out.println(">>> Población finalizada con éxito.");
        return apartments.size() + contracts.size();
    }

    // --------- APARTMENTS (Including House and Duplex) ------------------------------

    public List<Apartment> populatePlainApartments(int qty) {
        List<Apartment> apartments = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        String[] furnish = {"furnished", "semi-furnished", "unfurnished"};

        for (int i = 0; i < qty; i++) {
            Apartment apt;
            int typeChoice = rnd.nextInt(3); 

            if (typeChoice == 0) {
                House h = new House();
                h.setPropertyType("House");
                h.setYardSize(rnd.nextInt(50, 300));
                h.setPool(rnd.nextBoolean() ? "yes" : "no");
                apt = h;} 
                
                else if (typeChoice == 1) {
                Duplex d = new Duplex();
                d.setPropertyType("Duplex");
                d.setBalcony("large");
                apt = d;} 
                
                else {
                apt = new Apartment();
                apt.setPropertyType("Apartment");
            }

            apt.setArea(rnd.nextInt(40, 400));
            apt.setBedrooms(rnd.nextInt(1, 5));
            apt.setBathrooms(rnd.nextInt(1, 3));
            apt.setAirconditioning(rnd.nextBoolean() ? "yes" : "no");
            apt.setFurnishingstatus(furnish[rnd.nextInt(furnish.length)]);
            apt.setPrice(0L);
            Apartment saved = apartmentService.createApartment(apt);
            apartments.add(saved);
        }
        return apartments;
    }

    // --------- SCHOOLS ------------------------------

    public List<School> populateSchools(int qty) {
        List<School> schools = new ArrayList<>();
        if (qty <= 0) return schools;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        String[] types = {"Pública", "Privada", "Concertada"};
        String[] distances = {"100m", "500m", "1km", "2.5km"};
        String[] levels = {"Infantil i Primària", "ESO i Batxillerat", "Educació Especial"};
        String[] names = {"Escola Gravi", "Escola Palcam", "Escola Paideia", "Institut Montnegre"};

        for (int i = 0; i < qty; i++) {
            String name = names[rnd.nextInt(names.length)] + " " + (i + 1);
            String type = types[rnd.nextInt(types.length)];
            String dist = distances[rnd.nextInt(distances.length)];
            String level = levels[rnd.nextInt(levels.length)];
            String address = "Carrer de l'Exemple, " + rnd.nextInt(1, 500);
            School school = new School(null, name, type, address, dist);
            
            School savedSchool = schoolRepository.save(school);
            schools.add(savedSchool);
            System.out.println("✅ Escuela creada: " + savedSchool.getName() + " a " + savedSchool.getDistance());
        }

        return schools;
    }

   public boolean assignSchoolsToApartments(List<Apartment> apartments, List<School> schools) {
        if (apartments == null || schools == null || schools.isEmpty()) return false;
        
        for (Apartment apt : apartments) {
            apt.addSchool(schools.get(ThreadLocalRandom.current().nextInt(schools.size())));
            apartmentService.updateApartment(apt); 
        }
        return true;
    }

    // --------- REVIEWS & REVIEWERS ------------------------------

    public List<Reviewer> populateReviewers(int qty) {
        List<Reviewer> list = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            Reviewer r = new Reviewer("Reviewer " + i, "rev" + i + "@test.com", "Expert", 5);
            list.add(reviewerRepository.save(r));
        }
        return list;
    }

    public void assignReviewsToApartments(List<Apartment> apartments, List<Reviewer> reviewers) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (Apartment apt : apartments) {
            Review rev = new Review("Great Property", "Very good conditions", 5, LocalDate.now());
            rev.setApartment(apt);
            rev.setReviewer(reviewers.get(rnd.nextInt(reviewers.size())));
            reviewRepository.save(rev);
        }
    }

    // --------- OWNERS & CONTRACTS ------------------------------

    public List<Owner> populateOwners(int qty) {
        List<Owner> list = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            Owner o = new Owner("Owner " + i, "owner" + i + "@mail.com", 35, true, false, "DNI" + i, LocalDate.now(), 100);
            list.add(ownerRepository.save(o));
        }
        return list;
    }

    public List<PropertyContract> populatePropertyContracts(List<Apartment> apartments, List<Owner> owners) {
        List<PropertyContract> contracts = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (Apartment apt : apartments) {
            Owner selectedOwner = owners.get(rnd.nextInt(owners.size()));
            PropertyContract pc = new PropertyContract(selectedOwner, apt, "Contract-" + apt.getId(), LocalDate.now(), apt.getPrice().doubleValue());
            contracts.add(contractRepository.save(pc));
        }
        return contracts;
    }
}