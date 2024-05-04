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
import com.cw.dao.PatientDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.MedicalRecord;
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
    public Response getMedicalRecordById(@PathParam("medicalRecordId") int medicalRecordId) {
        LOGGER.info("Getting medical record by medical record Id: {}", medicalRecordId);
        MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(medicalRecordId);
        if (medicalRecord != null) {
            return Response.ok(medicalRecord).build();
        } else {
            throw new EntityNotFoundException("Medical record with ID " + medicalRecordId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            throw new MissingRequestBodyException("Medical record information is missing");
        }
        int patientId = medicalRecord.getPatient().getPatientId();

        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient id is missing");
        }

        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }
        medicalRecordDAO.addMedicalRecord(medicalRecord);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{medicalRecordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId, MedicalRecord updatedMedicalRecord) {
        if (updatedMedicalRecord == null) {
            throw new MissingRequestBodyException("Medical record information is missing for medical record Id " + medicalRecordId);
        }
        MedicalRecord existingPerson = medicalRecordDAO.getMedicalRecordById(medicalRecordId);

        if (existingPerson != null) {
            updatedMedicalRecord.setId(medicalRecordId);
            medicalRecordDAO.updateMedicalRecord(updatedMedicalRecord);
            return Response.status(Response.Status.OK)
                    .entity("Updated the details of medical record with medical record Id " + medicalRecordId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Medical record with medical record Id " + medicalRecordId + " not found to update.");
    }

    @DELETE
    @Path("/{medicalRecordId}")
    public Response deleteMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId) {
        medicalRecordDAO.deleteMedicalRecord(medicalRecordId);
        return Response.ok().build();
    }
}
