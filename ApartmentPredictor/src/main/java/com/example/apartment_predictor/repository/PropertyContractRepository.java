package com.example.apartment_predictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.apartment_predictor.model.PropertyContract;

@Repository
public interface PropertyContractRepository extends JpaRepository<PropertyContract, String> {
    @Modifying
    @Transactional
    void deleteByApartmentId(String apartmentId);
}