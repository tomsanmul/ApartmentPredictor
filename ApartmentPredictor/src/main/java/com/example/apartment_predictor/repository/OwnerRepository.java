package com.example.apartment_predictor.repository;

import com.example.apartment_predictor.model.Owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {}