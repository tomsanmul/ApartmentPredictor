package com.example.apartment_predictor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.repository.PropertyContractRepository;

@RestController
@RequestMapping("/api/contracte")
@CrossOrigin(origins = "*")
public class PropertyContractRestController {

    @Autowired 
    private PropertyContractRepository contractRepository;

    @GetMapping("/llistar")
    public List<PropertyContract> obtenirTots() {
        return contractRepository.findAll();
    }

    @GetMapping("/detalls/{id}")
    public ResponseEntity<PropertyContract> buscarPerId(@PathVariable String id) {
        return contractRepository.findById(id)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/nou")
    public ResponseEntity<PropertyContract> crearContracte(@RequestBody PropertyContract nouContracte) {
        try {
            PropertyContract guardat = contractRepository.save(nouContracte);
            return new ResponseEntity<>(guardat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarContracte(@PathVariable String id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}