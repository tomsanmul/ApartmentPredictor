package com.example.apartment_predictor.model;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class School {

    @Id
    private String id;
    private String name;
    private String type;
    private String location;
    private String distance;
    private String educationlevel;
    private int rating;
    private boolean isPublic;

    // Default constructor
    public School() {
    }

    public School(String id, String name, String type, String location, String distance) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.distance = distance;
        this.isPublic = true;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(String educationlevel) {
        this.educationlevel = educationlevel;
    }


}
