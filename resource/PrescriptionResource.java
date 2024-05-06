/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.PrescriptionDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Prescription;
import com.cw.validation.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/prescriptions")
public class PrescriptionResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);

    // Data Access Object for managing prescriptions
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    // Retrieve all prescriptions
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        return prescriptionDAO.getAllPrescriptions();
    }

    // Retrieve prescription by ID
    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionById(@PathParam("prescriptionId") String prescriptionIdParam) {
        // Validate and parse prescription ID
        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");

        // Check if prescription exists
        checkExistingPrescription(prescriptionId, "get");

        // Retrieve prescription by ID
        LOGGER.info("Getting prescription by ID: {}", prescriptionId);
        Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionId);

        return Response.ok(prescription).build();
    }

    // Add a new prescription
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        // Validate request body
        RequestErrorHandler.checkNullRequestBody(prescription);
        // Validate and set patient and doctor
        prescription.setPatient(RequestErrorHandler.validatePatientId(prescription.getPatient()));
        prescription.setDoctor(RequestErrorHandler.validateDoctorId(prescription.getDoctor()));

        // Add prescription
        prescriptionDAO.addPrescription(prescription);

        return Response.status(Response.Status.CREATED)
                .entity("Prescription successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    // Update an existing prescription
    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionId") String prescriptionIdParam, Prescription updatedPrescription) {
        // Validate and parse prescription ID
        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");

        // Validate request body
        RequestErrorHandler.checkNullRequestBody(updatedPrescription);
        // Check if prescription exists
        checkExistingPrescription(prescriptionId, "update");
        // Validate and set patient and doctor
        updatedPrescription.setPatient(RequestErrorHandler.validatePatientId(updatedPrescription.getPatient()));
        updatedPrescription.setDoctor(RequestErrorHandler.validateDoctorId(updatedPrescription.getDoctor()));

        // Update prescription
        updatedPrescription.setId(prescriptionId);
        prescriptionDAO.updatePrescription(updatedPrescription);

        // Log success message
        LOGGER.info("Updated prescription with ID {}: {}", prescriptionId, updatedPrescription);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of prescription with prescription ID " + prescriptionId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a prescription by ID
    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@PathParam("prescriptionId") String prescriptionIdParam) {
        // Validate and parse prescription ID
        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");

        // Check if prescription exists
        checkExistingPrescription(prescriptionId, "delete");

        // Delete prescription
        LOGGER.info("Deleting prescription with ID: {}", prescriptionId);
        prescriptionDAO.deletePrescription(prescriptionId);

        return Response.status(Response.Status.OK)
                .entity("Deleted prescription with prescription ID " + prescriptionId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a prescription exists
    private void checkExistingPrescription(int prescriptionId, String methodName) throws EntityNotFoundException {
        Prescription existingPrescription = prescriptionDAO.getPrescriptionById(prescriptionId);
        if (existingPrescription == null) {
            // Log error message
            LOGGER.error("Prescription with ID {} not found to {}", prescriptionId, methodName);
            throw new EntityNotFoundException("Prescription with ID " + prescriptionId + " not found to " + methodName);
        }
    }
}