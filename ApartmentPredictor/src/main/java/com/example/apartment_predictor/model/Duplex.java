package com.example.apartment_predictor.model;

import java.util.UUID;

public class Duplex extends Apartment {

    private String balcony;
    private String elevator;


    public Duplex() {
        this.id = UUID.randomUUID().toString();
    }

    public Duplex(String balcony, String elevator, String airconditioning, String garden, int garageQty, String roofType) {
        this.id = UUID.randomUUID().toString();
        this.balcony = balcony;
        this.elevator = elevator;

    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }



    @Override
    public String toString() {
        return "Duplex{" +
                "id='" + id + '\'' +
                ", balcony='" + balcony + '\'' +
                ", elevator='" + elevator + '\'' +

                '}';
    }
}
