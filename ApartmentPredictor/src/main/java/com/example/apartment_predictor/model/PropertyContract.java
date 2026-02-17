package com.example.apartment_predictor.model;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "property_contracts")
public class PropertyContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonBackReference // Evita que en carregar l'amo torni a carregar el contracte i entri en bucle
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    private String contractDetails;
    private LocalDate contractDate;
    private Double finalPrice;

    public PropertyContract(){}
    
    public PropertyContract(Owner owner, Apartment apartment, String contractDetails, LocalDate contractDate, Double finalPrice){
        this.owner = owner;
        this.apartment = apartment;
        this.contractDetails = contractDetails;
        this.contractDate = contractDate;
        this.finalPrice = finalPrice;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public Owner getOwner(){return owner;}
    public void setOwner(Owner owner){this.owner = owner;}
    public Apartment getApartment(){return apartment;}
    public void setApartment(Apartment apartment){this.apartment = apartment;}
    public String getContractDetails(){return contractDetails;}
    public void setContractDetails(String details){this.contractDetails = details;}
    public LocalDate getContractDate(){return contractDate;}
    public void setContractDate(LocalDate date){this.contractDate = date;}
    public Double getFinalPrice(){return finalPrice;}
    public void setFinalPrice(Double price){this.finalPrice = price;}
}