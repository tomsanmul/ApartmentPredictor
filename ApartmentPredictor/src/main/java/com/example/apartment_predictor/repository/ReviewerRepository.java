package com.example.apartment_predictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartment_predictor.model.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {}