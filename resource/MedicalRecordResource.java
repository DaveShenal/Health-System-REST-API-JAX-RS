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

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordResource.class);

    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordDAO.getAllMedicalRecords();
    }

    @GET
    @Path("/{medicalRecordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordById(@PathParam("medicalRecordId") String recordIdParam) {

        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "nedical record");
        checkExistingMedicalRecord(recordId, "get");

        LOGGER.info("Getting medical record by record Id: {}", recordId);
        MedicalRecord record = medicalRecordDAO.getMedicalRecordById(recordId);

        return Response.ok(record).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {

        RequestErrorHandler.checkNullRequestBody(medicalRecord);
        medicalRecord.setPatient(RequestErrorHandler.validatePatientId(medicalRecord.getPatient()));

        medicalRecordDAO.addMedicalRecord(medicalRecord);
        return Response.status(Response.Status.CREATED)
                .entity("Medical record successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @PUT
    @Path("/{medicalRecordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("medicalRecordId") String recordIdParam, MedicalRecord updatedRecord) {

        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "record");
        RequestErrorHandler.checkNullRequestBody(updatedRecord);
        checkExistingMedicalRecord(recordId, "update");
        updatedRecord.setPatient(RequestErrorHandler.validatePatientId(updatedRecord.getPatient()));

        updatedRecord.setId(recordId);
        medicalRecordDAO.updateMedicalRecord(updatedRecord);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of medical record with record Id " + recordId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{medicalRecordId}")
    public Response deleteMedicalRecord(@PathParam("medicalRecordId") String recordIdParam) {

        int recordId = RequestErrorHandler.validateIdParam(recordIdParam, "medical record");
        checkExistingMedicalRecord(recordId, "delete");

        LOGGER.info("Deleting medical record with ID: {}", recordId);
        medicalRecordDAO.deleteMedicalRecord(recordId);
        return Response.status(Response.Status.OK)
                .entity("Deleted medical record with record Id " + recordId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    private void checkExistingMedicalRecord(int recordId, String methodName) throws EntityNotFoundException {
        MedicalRecord existingRecord = medicalRecordDAO.getMedicalRecordById(recordId);
        if (existingRecord == null) {
            throw new EntityNotFoundException("Medical Record with ID " + recordId + " not found to " + methodName);
        }
    }
}
