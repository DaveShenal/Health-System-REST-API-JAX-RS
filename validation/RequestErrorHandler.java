/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.validation;

/**
 *
 * @author daves
 */
import com.cw.dao.DoctorDAO;
import com.cw.dao.PatientDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.InvalidIdFormatException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.exception.MissingRequiredFieldException;
import com.cw.model.Doctor;
import com.cw.model.Patient;

public class RequestErrorHandler {

    public static void validateEntitiy(Validator entity, String EntityName) {
        if (entity == null) {
            throw new MissingRequestBodyException(EntityName + " Informations are missing in the "
                    + "request body to perform this method");
        }
        if (!entity.areAllFieldsFilled()) {
            throw new MissingRequiredFieldException(EntityName);
        }
    }

    public static Doctor validateDoctorId(Doctor doctor) {
        int doctorId = doctor.getDoctorId();
        if (doctorId == 0) {
            throw new MissingRequestBodyException("Doctor ID is missing");
        }
        if (!DoctorDAO.doctorIsExist(doctorId)) {
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found.");
        }
        return DoctorDAO.getDoctorById(doctorId);
    }

    public static Patient validatePatientId(Patient Patient) {
        int patientId = Patient.getPatientId();

        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient ID is missing");
        }
        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }
        return PatientDAO.getPatientById(patientId);
    }

    public static int validateIdParam(String idParam, String className) {
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new InvalidIdFormatException("Invalid " + className
                    + " ID format in the endpoint: " + idParam);
        }
        return id;
    }

}
