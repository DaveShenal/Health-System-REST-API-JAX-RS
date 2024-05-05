/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Appointment;
import com.cw.model.Doctor;
import com.cw.model.Patient;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private static List<Appointment> appointments = new ArrayList<>();

    static {
        appointments.add(new Appointment(1, "2024-05-06","9.00 AM",
                new Patient(4, "No history", "Fair", 10, "John", "john@example.com", "123 Main St"),
                new Doctor(4, "Surgery", 11, "Ben", "ben@example.com", "7/34 Castle St")));
        appointments.add(new Appointment(2, "2024-04-28","2.00 PM",
                new Patient(5, "No Significent medical history", "Good", 12, "Stanly", "stanly@example.com", "123 Main St"),
                new Doctor(5, "Surgery", 13, "Ben", "ben@example.com", "7/34 Castle St")));
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment getAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;
    }

    public void addAppointment(Appointment appointment) {
        int nextId = getNextUserId();
        appointment.setId(nextId);
        appointments.add(appointment);
    }

    public void updateAppointment(Appointment updatedAppointment) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getId() == updatedAppointment.getId()) {
                appointments.set(i, updatedAppointment);               
                break;
            }
        }
    }

    public void deleteAppointment(int id) {
        appointments.removeIf(appointment -> appointment.getId() == id);
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Appointment appointment : appointments) {
            if (appointment.getId() > maxUserId) {
                maxUserId = appointment.getId();
            }
        }
        return maxUserId + 1;
    }
}
