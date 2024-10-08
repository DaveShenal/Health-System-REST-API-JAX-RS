/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.model;

import com.cw.validation.Validator;

/**
 *
 * @author daves
 */
public class Patient extends Person implements Validator{

    private int patientId;
    private String medicalHistory;
    private String currentHealthStatus;

    // Constructors
    public Patient() {
    }

    public Patient(int patientId, String medicalHistory, String currentHealthStatus, int personId, String name, String contactInformation, String address) {
        super(personId, name, contactInformation, address);
        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
        this.currentHealthStatus = currentHealthStatus;
    }

    // Getters and setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getCurrentHealthStatus() {
        return currentHealthStatus;
    }

    public void setCurrentHealthStatus(String currentHealthStatus) {
        this.currentHealthStatus = currentHealthStatus;
    }

    @Override
    public boolean areAllFieldsFilled() {
        return medicalHistory != null && !medicalHistory.isEmpty() && currentHealthStatus != null && !currentHealthStatus.isEmpty();
    }
}
