/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.model;

/**
 *
 * @author daves
 */
public class Doctor extends Person {

    private int doctorId;
    private String specialization;

    // Constructors
    public Doctor() {
    }

    public Doctor(int doctorId, String specialization, int personId, String name, String contactInformation, String address) {
        super(personId, name, contactInformation, address);
        this.doctorId = doctorId;
        this.specialization = specialization;
    }

    // Getters and setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
