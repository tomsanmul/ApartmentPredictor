package com.example.apartment_predictor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    //to do
    private Long id;
    private String name;
    private String email;

    //Constructor vacío
    public Person(){       
    }

    //Cosntructor
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //Getters Setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getId(){ return id;}
    public void setId(Long id) {this.id = id;} 


}
