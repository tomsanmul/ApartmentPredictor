package com.example.apartment_predictor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.repository.OwnerRepository;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerRepository ownerRepo;

    public OwnerController(OwnerRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @GetMapping
    public List<Owner> getAll() {
        List<Owner> owners = new ArrayList<>();
        ownerRepo.findAll().forEach(owners::add);
    return owners;
}
}