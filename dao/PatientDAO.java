/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Patient;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends PersonDAO {

    private static List<Patient> patients = new ArrayList<>();

    static {
        Patient patient1 = new Patient(1, "No Significent medical history", "Good", 7, "Stanly", "stanly@example.com", "123 Main St");
        Patient patient2 = new Patient(2, "No Significent medical history", "Good", 8, "Stanly", "stanly@example.com", "123 Main St");
        Patient patient3 = new Patient(3, "No History", "Excellent", 9, "Robert", "robert@example.com", "7/67 Iwan St");

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        PersonDAO.persons.add(patient1);
        PersonDAO.persons.add(patient2);
        PersonDAO.persons.add(patient3);
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getPatientId() == id) {
                return patient;
            }
        }
        return null;
    }

    public void addPatient(Patient patient) {
        int nextId = getNextUserId();
        int nextPersonId = super.getNextPersonId();
        patient.setPatientId(nextId);
        patient.setPersonId(nextPersonId);
        patients.add(patient);
    }

    public void updatePatient(Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            if (patient.getPatientId() == updatedPatient.getPatientId()) {
                patients.set(i, updatedPatient);
                System.out.println("Patient updated: " + updatedPatient.toString());
                break;
            }
        }
    }

    public void deletePatient(int id) {
        patients.removeIf(patient -> patient.getPatientId() == id);
    }
    
        public void deleteDoctor(int id) {
        for (Patient patient : patients) {
            if (patient.getPatientId() == id) {
                int patientId = patient.getPersonId();                          
                PersonDAO.deletePerson(patientId);
                patients.remove(patient);
                break;
            }
        }
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Patient patient : patients) {
            if (patient.getPatientId() > maxUserId) {
                maxUserId = patient.getPatientId();
            }
        }
        return maxUserId + 1;
    }

    public static boolean patientIsExist(int patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId() == patientId) {
                return true; // Patient with the specified ID exists
            }
        }
        return false; // Patient with the specified ID does not exist
    }
}
