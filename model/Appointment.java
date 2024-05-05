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
    private String date;
    private String time;
    private Patient patient;
    private Doctor doctor;

    public Appointment() {
    }

    public Appointment(int id, String date,String time, Patient patient, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateTime) {
        this.date = dateTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
                + "Date : " + date + "\n"
                + "Time : " + time + "\n"
                + patient.toString() + "\n"
                + doctor.toString();
    }

}
