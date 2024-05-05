/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Doctor;
import com.cw.model.Patient;
import com.cw.model.Prescription;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    private static List<Prescription> prescriptions = new ArrayList<>();

    static {
        prescriptions.add(new Prescription(1,
                new Patient(11, "No History", "Excellent", 19, "Robert", "robert@example.com", "7/67 Iwan St"),
                new Doctor(6, "Cardiologist", 20, "Sam", "sam@example.com", "10/54 Green St"),
                "Aspirin", "500mg", "Take with water", "7 days"));
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptions;
    }

    public Prescription getPrescriptionById(int id) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == id) {
                return prescription;
            }
        }
        return null;
    }

    public void addPrescription(Prescription prescription) {
        int nextId = getNextUserId();
        prescription.setId(nextId);
        prescriptions.add(prescription);
    }

    public void updatePrescription(Prescription updatedPrescription) {
        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription prescription = prescriptions.get(i);
            if (prescription.getId() == updatedPrescription.getId()) {
                prescriptions.set(i, updatedPrescription);               
                break;
            }
        }
    }

    public void deletePrescription(int id) {
        prescriptions.removeIf(prescription -> prescription.getId() == id);
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() > maxUserId) {
                maxUserId = prescription.getId();
            }
        }
        return maxUserId + 1;
    }
}
