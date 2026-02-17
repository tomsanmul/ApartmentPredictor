package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/critic")
@CrossOrigin(origins = "*")
public class ReviewerRestController {

    @Autowired 
    private ReviewerRepository reviewerRepo;


    @GetMapping("/tots")
    public List<Reviewer> llistarCritics() {
        return reviewerRepo.findAll();
    }

    @GetMapping("/detalls/{id}")
    public ResponseEntity<Reviewer> obtenirPerId(@PathVariable Long id) {
        return reviewerRepo.findById(id)
                .map(crit -> new ResponseEntity<>(crit, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/nou")
    public ResponseEntity<Reviewer> registrarCritic(@RequestBody Reviewer nouCritic) {
        Reviewer guardat = reviewerRepo.save(nouCritic);
        return new ResponseEntity<>(guardat, HttpStatus.CREATED);
    }

    @PutMapping("/actualitzar/{id}")
    public ResponseEntity<Reviewer> modificarCritic(@PathVariable Long id, @RequestBody Reviewer dades) {
        return reviewerRepo.findById(id).map(existent -> {
            // Camps heretats de la classe Person
            existent.setName(dades.getName());
            existent.setEmail(dades.getEmail());
            
            // Camps propis de Reviewer.java
            existent.setDescription(dades.getDescription());
            existent.setExperienceYears(dades.getExperienceYears());
            
            Reviewer actualitzat = reviewerRepo.save(existent);
            return new ResponseEntity<>(actualitzat, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> esborrarCritic(@PathVariable Long id) {
        if (reviewerRepo.existsById(id)) {
            reviewerRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}