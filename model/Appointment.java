/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.model;

/**
 *
 * @author daves
 */
import java.time.LocalDateTime;

public class Appointment {

    private int id;
    private String dateTime;
    private Patient patient;
    private Doctor doctor;

    public Appointment() {
    }

    public Appointment(int id, String dateTime, Patient patient, Doctor doctor) {
        this.id = id;
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + id + "\n"
                + "Date and Time: " + dateTime + "\n"
                + patient.toString() + "\n"
                + doctor.toString();
    }

}
