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
public class Billing implements Validator{

    private int id;
    private Patient patient;
    private double amount;
    private boolean isPaid;

    public Billing() {
    }

    public Billing(int id, Patient patient, double amount, boolean isPaid) {
        this.id = id;
        this.patient = patient;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public boolean areAllFieldsFilled() {
        return patient != null && amount != 0.0;
    }

}
