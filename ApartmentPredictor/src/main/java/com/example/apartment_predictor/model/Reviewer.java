package com.example.apartment_predictor.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("REVIEWER")
public class Reviewer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private String description;
    private int experienceYears;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public Reviewer() { super(); }

    public Reviewer(String name, String email, String description, int experienceYears) {
        this.setName(name);
        this.setEmail(email);
        this.description = description;
        this.experienceYears = experienceYears;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setReviewer(this);
    }

    // Getters i Setters específics
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}