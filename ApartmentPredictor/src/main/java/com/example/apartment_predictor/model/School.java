package com.example.apartment_predictor.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class School {

    @Id
    private String id;
    private String name;
    private String type;
    private String location;
    
    @Column(name = "latitude")
    @NotNull(message = "Latitude is required")
    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    private Double latitude;
    
    @Column(name = "longitude")
    @NotNull(message = "Longitude is required")
    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    private Double longitude;
    
    private int rating;
    private boolean isPublic;

    // Default constructor
    public School() {
        this.id = UUID.randomUUID().toString();
    }

    public School(String name, String type, String location, Double latitude, Double longitude, int rating, boolean isPublic) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
        return "School [id=" + id + ", name=" + name + ", type=" + type + ", location=" + location + ", latitude=" + latitude + ", longitude=" + longitude + ", rating=" + rating + ", isPublic=" + isPublic + "]";
    }


}
