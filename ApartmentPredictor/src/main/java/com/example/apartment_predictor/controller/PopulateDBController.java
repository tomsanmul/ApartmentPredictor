package com.example.apartment_predictor.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;
import com.example.apartment_predictor.utils.PopulateDB;

@RestController
@RequestMapping("/api/populate")
public class PopulateDBController {

    @Autowired
    private PopulateDB populateDB;

    @Autowired
    private ApartmentService apartmentService;

    // Estils visuals nets
    private final String BASE_STYLE = "font-family: 'Segoe UI', sans-serif; padding: 40px; background-color: #f0f4f8; display: flex; flex-direction: column; align-items: center;";
    private final String CARD_STYLE = "padding: 30px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); background: white; text-align: center; width: 90%; max-width: 700px; margin-bottom: 20px;";
    private final String BUTTON_BASE = "text-decoration: none; color: white; padding: 12px 24px; border-radius: 8px; font-weight: bold; border: none; cursor: pointer; transition: 0.3s; margin: 5px; display: inline-block;";

    
    @GetMapping("/run")
    public String runPopulate() {
        try {

            //No puc convertir Iterable en LIST    
            //List<Apartment> apartments = apartmentService.findAll();
            
            //PARCHE PER CONVERTIR EL ITERABLE en LIST NO RECOMENDABLE, NOMÉS PER SORTIR DEL PAS!! AIXÒ S'HA DE CANVIAR
            List<Apartment> apartments = StreamSupport.stream(apartmentService.findAll().spliterator(), false).toList();
           

            // Llista d'apartaments (només visualització)
            String llistaHtml = apartments.stream().map(apt -> 
                "<div style='display: flex; justify-content: flex-start; align-items: center; padding: 12px; border-bottom: 1px solid #edf2f7;'>" +
                "  <span style='color: #2d3748; font-size: 1.1rem;'>🏠 <strong>ID " + apt.getId() + "</strong> — " + apt.getPropertyType() + "</span>" +
                "</div>"
            ).collect(Collectors.joining());

            // Panell d'accions només amb botons de creació
            String actionPanel = 
                "<div style='display: flex; justify-content: center; gap: 20px; margin-top: 20px; padding: 20px; background: #f8fafc; border-radius: 12px; border: 1px solid #e2e8f0;'>" +
                "  <a href='/api/populate/create_one' style='" + BUTTON_BASE + " background: #3182ce;'>➕ Crear 1 Aleatori</a>" +
                "  <a href='/api/populate/reset' style='" + BUTTON_BASE + " background: #38b2ac;'>🔄 Reset Total (3 originals)</a>" +
                "</div>";

            return "<html><body style=\"" + BASE_STYLE + "\">" +
                   "  <div style=\"" + CARD_STYLE + " border-top: 6px solid #3182ce;\">" +
                   "    <h1 style='color: #2d3748; margin: 0;'>Marc Apartment Manager</h1>" +
                   "    <p style='color: #718096;'>Gestió del teu duplex, casa i més.</p>" +
                   "    " + actionPanel +
                   "  </div>" +
                   "  <div style=\"" + CARD_STYLE + "\">" +
                   "    <h2 style='text-align: left; color: #2d3748; border-bottom: 2px solid #edf2f7; padding-bottom: 10px;'>Immobles Actuals (" + apartments.size() + ")</h2>" +
                   "    <div style='max-height: 450px; overflow-y: auto; text-align: left;'>" + 
                          (apartments.isEmpty() ? "<p style='color: #a0aec0; padding: 20px;'>No hi ha dades a la base de dades.</p>" : llistaHtml) + 
                   "    </div>" +
                   "  </div>" +
                   "</body></html>";
        } catch (Exception e) {
            return "<html><body style=\"" + BASE_STYLE + "\"><h2>Error</h2><p>" + e.getMessage() + "</p></body></html>";
        }
    }

    @GetMapping("/create_one")
    public String createOne() {
        populateDB.populateAll(1);
        return runPopulate();
    }

    /*
    @GetMapping("/reset")
    public String resetAll() {
        apartmentService.executeFullReset();
        return runPopulate();
    }
    */

}