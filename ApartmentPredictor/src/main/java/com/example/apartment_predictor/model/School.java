package com.example.apartment_predictor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class School {

    @Id
    private String id;
    private String name;
    private String type;
    private String location;
    private int rating;
    private boolean isPublic;

    // Default constructor
    public School() {
        this.id = UUID.randomUUID().toString();
    }

    public School(String name, String type, String location, int rating, boolean isPublic) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.location = location;
        this.rating = rating;
        this.isPublic = isPublic;
    }

    //@ManyToMany
    //private List<Apartment> apartments = new ArrayList<>();

    //getters and setters and toString
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "School [id=" + id + ", name=" + name + ", type=" + type + ", location=" + location + ", rating=" + rating + ", isPublic=" + isPublic + "]";
    }


}
