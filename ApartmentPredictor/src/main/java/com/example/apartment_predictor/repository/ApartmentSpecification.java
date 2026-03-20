package com.example.apartment_predictor.repository;
import org.springframework.data.jpa.domain.Specification;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.model.School;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class ApartmentSpecification {

    public static Specification<Apartment> filterBy(
            Long maxPrice,              // ≤ this price
            Integer minArea,            // ≥ this area (sq ft / m²)
            Integer minBedrooms,
            Integer minBathrooms,
            Integer minParking,
            String furnishingStatus,    // "furnished", "semi-furnished", "unfurnished" (partial match)
            Boolean mainroad,           // yes/no
            Boolean guestroom,
            Boolean basement,
            Boolean hotwaterheating,
            Boolean airconditioning,
            Boolean prefarea,            // preferred area yes/no
            Integer minReviews,
            Integer minSchools,
            String textToSearch
    ) {
        return (Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate p = cb.conjunction();   // starts as "true"

            // ─────────────────────────────────────────────
            // Price – most important filter
            // ─────────────────────────────────────────────
            if (maxPrice != null && maxPrice > 0) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // ─────────────────────────────────────────────
            // Area (size)
            // ─────────────────────────────────────────────
            if (minArea != null && minArea > 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("area"), minArea));
            }

            // ─────────────────────────────────────────────
            // Bedrooms & Bathrooms
            // ─────────────────────────────────────────────
            if (minBedrooms != null && minBedrooms > 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms));
            }

            if (minBathrooms != null && minBathrooms > 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms));
            }

            // ─────────────────────────────────────────────
            // Parking spaces
            // ─────────────────────────────────────────────
            if (minParking != null && minParking > 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("parking"), minParking));
            }

            // ─────────────────────────────────────────────
            // Furnishing status (partial / case-insensitive)
            // ─────────────────────────────────────────────
            if (isNotBlank(furnishingStatus)) {
                p = cb.and(p,
                    cb.like(
                        cb.lower(root.get("furnishingstatus")),
                        "%" + furnishingStatus.trim().toLowerCase() + "%"
                    )
                );
            }

            // ─────────────────────────────────────────────
            // Yes/No fields (Boolean-like strings: "yes"/"no")
            // ─────────────────────────────────────────────
            p = addYesNoFilter(p, cb, root, "mainroad", mainroad);
            p = addYesNoFilter(p, cb, root, "guestroom", guestroom);
            p = addYesNoFilter(p, cb, root, "basement", basement);
            p = addYesNoFilter(p, cb, root, "hotwaterheating", hotwaterheating);
            p = addYesNoFilter(p, cb, root, "airconditioning", airconditioning);
            p = addYesNoFilter(p, cb, root, "prefarea", prefarea);


            // ─────────────────────────────
            // Filtro por número mínimo de reviews
            // ─────────────────────────────
            if (minReviews != null && minReviews > 0) {
                Join<Apartment, Review> reviewJoin = root.join("reviews", JoinType.LEFT);
                query.groupBy(root.get("id")); // necesario para contar
                query.having(cb.greaterThanOrEqualTo(cb.count(reviewJoin), minReviews.longValue()));
            }


            // ─────────────────────────────
            // Filtro por texto en reviews
            // ─────────────────────────────
            if (textToSearch != null && !"".equals(textToSearch)) {
                Join<Apartment, Review> reviewJoin = root.join("reviews", JoinType.LEFT);
                p = cb.and(p, cb.like( cb.lower(reviewJoin.get("content")),"%" + textToSearch.trim().toLowerCase() + "%" )
            );
                query.distinct(true); // evita duplicados por el JOIN
                System.out.println(query);
            }


             // ─────────────────────────────
            // Filtro por número mínimo de Schools
            // ─────────────────────────────
            if (minSchools != null && minSchools > 0) {
                Join<Apartment, School> SchoolsJoin = root.join("schools", JoinType.LEFT);
                query.groupBy(root.get("id")); // necesario para contar
                query.having(cb.greaterThanOrEqualTo(cb.count(SchoolsJoin), minSchools.longValue()));
            }

                return p;
            };
    }

    // Small helper – makes yes/no filters cleaner
    private static Predicate addYesNoFilter(
            Predicate current,
            CriteriaBuilder cb,
            Root<Apartment> root,
            String fieldName,
            Boolean value) {

        if (value != null) {
            String expected = value ? "yes" : "no";
            return cb.and(current,
                cb.equal(
                    cb.lower(root.get(fieldName)),
                    expected
                )
            );
        }
        return current;
    }

    // Small utility – checks string is not null/empty/whitespace
    private static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}