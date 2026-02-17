package com.example.apartment_predictor.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apartment_predictor.model.Review;
public interface ReviewRepository extends JpaRepository<Review, Long> {}