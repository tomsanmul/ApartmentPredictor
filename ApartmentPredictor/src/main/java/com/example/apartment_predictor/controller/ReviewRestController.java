package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "*")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepo;

    @GetMapping("/all")
    public List<Review> llistarReviews() {
        return reviewRepo.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Review> guardarReview(@RequestBody Review r) {
        if (r.getDate() == null) {
            r.setDate(java.time.LocalDate.now());
        }
        Review nova = reviewRepo.save(r);
        return new ResponseEntity<>(nova, HttpStatus.CREATED);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> esborrar(@PathVariable Long id) {
        if (reviewRepo.existsById(id)) {
            reviewRepo.deleteById(id);
            return new ResponseEntity<>("Review eliminada", HttpStatus.OK);
        }
        return new ResponseEntity<>("No s'ha trobat la review", HttpStatus.NOT_FOUND);
    }
}