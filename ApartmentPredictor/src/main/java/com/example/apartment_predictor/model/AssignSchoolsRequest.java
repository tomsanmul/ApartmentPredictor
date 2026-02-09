package com.example.apartment_predictor.model;

import java.util.List;

//ESTO ES UN DTO  (Data Tranfer Object)

public class AssignSchoolsRequest {

    private String apartmentId;
    private List<School> schools;

    public AssignSchoolsRequest() {
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }
}