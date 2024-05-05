/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Doctor;
import com.cw.model.Person;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends PersonDAO {

    private static List<Doctor> doctors = new ArrayList<>();

    static {
        Doctor doctor1 = new Doctor(1, "Surgery", 4, "Ben", "ben@example.com", "7/34 Castle St");
        Doctor doctor2 = new Doctor(2, "Cardiologist", 5, "Sam", "sam@example.com", "10/54 Green St");
        Doctor doctor3 = new Doctor(3, "General Practitioner", 6, "Tom", "tom@example.com", "14/19  St");

        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);

        PersonDAO.persons.add(doctor1);
        PersonDAO.persons.add(doctor2);
        PersonDAO.persons.add(doctor3);

    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public static Doctor getDoctorById(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == id) {
                return doctor;
            }
        }
        return null;
    }

    public void addDoctor(Doctor doctor) {
        int nextId = getNextUserId();
        int nextPersonId = super.getNextPersonId();
        doctor.setDoctorId(nextId);
        doctor.setPersonId(nextPersonId);
        doctors.add(doctor);
        PersonDAO.persons.add(doctor);
    }

    public void updateDoctor(Doctor updatedDoctor) {
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            if (doctor.getDoctorId() == updatedDoctor.getDoctorId()) {
                doctors.set(i, updatedDoctor);
                System.out.println("Doctor updated: " + updatedDoctor.toString());
                break;
            }
        }
    }

    public void deleteDoctor(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == id) {
                int personID = doctor.getPersonId();                          
                PersonDAO.deletePerson(personID);
                doctors.remove(doctor);
                break;
            }
        }
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() > maxUserId) {
                maxUserId = doctor.getDoctorId();
            }
        }
        return maxUserId + 1;
    }

    public static boolean doctorIsExist(int doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == doctorId) {
                System.out.println("===================");
                return true; // Doctor with the specified ID exists
            }
        }
        return false; // Doctor with the specified ID does not exist
    }
}
