package com.example.apartment_predictor.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.apartment_predictor.model.Review;

public interface ReviewRepository extends CrudRepository<Review, String> {
}
