package com.example.apartment_predictor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.apartment_predictor.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {}