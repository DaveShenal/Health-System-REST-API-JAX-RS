/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.MedicalRecordDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.MedicalRecord;
import com.cw.utils.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/medicalRecords")
public class MedicalRecordResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordResource.class);

    // Data Access Object for managing medical records
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    // Retrieve all medical records
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        LOGGER.info("Retrieving all medical records.");
        return medicalRecordDAO.getAllMedicalRecords();
    }

    // Retrieve medical record by ID
    @GET
    @Path("/{medicalRecordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordById(@PathParam("medicalRecordId") String recordIdParam) {
        // Validate and parse medical record ID
        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "medical record");

        // Check if medical record exists
        checkExistingMedicalRecord(recordId, "get");

        // Retrieve medical record by ID
        LOGGER.info("Getting medical record by ID: {}", recordId);
        MedicalRecord record = medicalRecordDAO.getMedicalRecordById(recordId);

        return Response.ok(record).build();
    }

    // Add a new medical record
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        // Validate request body
        RequestErrorHandler.checkNullRequestBody(medicalRecord);
        // Validate and set patient
        medicalRecord.setPatient(RequestErrorHandler.validatePatientId(medicalRecord.getPatient()));

        // Add medical record
        medicalRecordDAO.addMedicalRecord(medicalRecord);

        // Log success message
        LOGGER.info("Added new medical record: {}", medicalRecord);

        return Response.status(Response.Status.CREATED)
                .entity("Medical record successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Update an existing medical record
    @PUT
    @Path("/{medicalRecordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("medicalRecordId") String recordIdParam, MedicalRecord updatedRecord) {
        // Validate and parse medical record ID
        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "medical record");

        // Validate request body
        RequestErrorHandler.checkNullRequestBody(updatedRecord);
        // Check if medical record exists
        checkExistingMedicalRecord(recordId, "update");
        // Validate and set patient
        updatedRecord.setPatient(RequestErrorHandler.validatePatientId(updatedRecord.getPatient()));

        // Update medical record
        updatedRecord.setId(recordId);
        medicalRecordDAO.updateMedicalRecord(updatedRecord);

        // Log success message
        LOGGER.info("Updated medical record with ID {}: {}", recordId, updatedRecord);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of medical record with record ID " + recordId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a medical record by ID
    @DELETE
    @Path("/{medicalRecordId}")
    public Response deleteMedicalRecord(@PathParam("medicalRecordId") String recordIdParam) {
        // Validate and parse medical record ID
        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "medical record");

        // Check if medical record exists
        checkExistingMedicalRecord(recordId, "delete");

        // Delete medical record
        LOGGER.info("Deleting medical record with ID: {}", recordId);
        medicalRecordDAO.deleteMedicalRecord(recordId);

        return Response.status(Response.Status.OK)
                .entity("Deleted medical record with record ID " + recordId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a medical record exists
    private void checkExistingMedicalRecord(int recordId, String methodName) throws EntityNotFoundException {
        MedicalRecord existingRecord = medicalRecordDAO.getMedicalRecordById(recordId);
        if (existingRecord == null) {
            // Log error message
            LOGGER.error("Medical record with ID {} not found to {}", recordId, methodName);
            throw new EntityNotFoundException("Medical record with ID " + recordId + " not found to " + methodName);
        }
    }
}