package com.example.apartment_predictor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity 

public class School {

    @Id private String id; 
    private String name; 
    private String type; 
    private String location; 
    private int rating; 
    private Boolean isPublic;

    // Default Constructor
    public School() {this.id = UUID.randomUUID().toString();}

    //Constructor with all fields
    public School(String id, String name, String type, String location, int rating, Boolean isPublic,
            List<Apartment> apartment) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.rating = rating;
        this.isPublic = isPublic;
        this.apartment = apartment;
    }


   @ManyToMany private List<Apartment> apartment = new ArrayList<>();

    //Getters i Setters

    public String getId() {return id;} 
    public void setId(String id) {this.id = id;}

    public String getName() {return name;} 
    public void setName(String name) {this.name = name;}
    
    public String getType() {return type;} 
    public void setType(String type) {this.type = type;}
    
    public String getLocation() {return location;} 
    public void setLocation(String location) {this.location = location;}
    
    public int getRating() {return rating;} 
    public void setRating(int rating) {this.rating = rating;}
    
    public Boolean getIsPublic() {return isPublic;} 
    public void setIsPublic(Boolean isPublic) {this.isPublic = isPublic;}

}
