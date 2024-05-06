/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.PatientDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Patient;
import com.cw.validation.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/patients")
public class PatientResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

    // Data Access Object for managing patients
    private PatientDAO patientDAO = new PatientDAO();

    // Retrieve all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients() {
        LOGGER.info("Retrieving all patients.");
        return patientDAO.getAllPatients();
    }

    // Retrieve patient by ID
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("patientId") String patientIdParam) {
        // Validate and parse patient ID
        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");

        // Check if patient exists
        checkExistingPatient(patientId, "get");

        // Retrieve patient by ID
        LOGGER.info("Getting patient by ID: {}", patientId);
        Patient patient = patientDAO.getPatientById(patientId);

        return Response.ok(patient).build();
    }

    // Add a new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        // Validate request body
        RequestErrorHandler.validateEntitiy(patient,"Patient");

        // Add patient
        LOGGER.info("Adding new patient: {}", patient);
        patientDAO.addPatient(patient);

        return Response.status(Response.Status.CREATED)
                .entity("Patient successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    // Update an existing patient
    @PUT
    @Path("/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("patientId") String patientIdParam, Patient updatedPatient) {
        // Validate and parse patient ID
        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");

        // Validate request body
        RequestErrorHandler.validateEntitiy(updatedPatient, "Patient");
        // Check if patient exists
        checkExistingPatient(patientId, "update");

        // Update patient
        updatedPatient.setPatientId(patientId);
        patientDAO.updatePatient(updatedPatient);

        // Log success message
        LOGGER.info("Updated patient with ID {}: {}", patientId, updatedPatient);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of patient with patient ID " + patientId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a patient by ID
    @DELETE
    @Path("/{patientId}")
    public Response deletePatient(@PathParam("patientId") String patientIdParam) {
        // Validate and parse patient ID
        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");

        // Check if patient exists
        checkExistingPatient(patientId, "delete");

        // Delete patient
        LOGGER.info("Deleting patient with ID: {}", patientId);
        patientDAO.deletePatient(patientId);

        return Response.status(Response.Status.OK)
                .entity("Deleted patient with patient ID " + patientId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a patient exists
    private void checkExistingPatient(int patientId, String methodName) throws EntityNotFoundException {
        Patient existingPatient = patientDAO.getPatientById(patientId);
        if (existingPatient == null) {
            // Log error message
            LOGGER.error("Patient with ID {} not found to {}", patientId, methodName);
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found to " + methodName);
        }
    }
}