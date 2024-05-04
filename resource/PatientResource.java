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
import com.cw.dao.PersonDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Patient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/patients")
public class PatientResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

    private PatientDAO patientDAO = new PatientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients() {
        LOGGER.info("Retrieving all patients.");
        return patientDAO.getAllPatients();
    }

    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("patientId") int patientId) {
        LOGGER.info("Getting patient by ID: {}", patientId);
        Patient patient = patientDAO.getPatientById(patientId);
        if (patient != null) {
            return Response.ok(patient).build();
        } else {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        if (patient == null) {
            throw new MissingRequestBodyException("Patient information is missing");
        }

        LOGGER.info("Adding new patient: {}", patient);
        patientDAO.addPatient(patient);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("patientId") int patientId, Patient updatedPatient) {
        if (updatedPatient == null) {
            throw new MissingRequestBodyException("Patient information is missing for patient Id " + patientId);
        }
        LOGGER.info("Updating patient with ID {}", patientId);
        Patient existingPerson = patientDAO.getPatientById(patientId);

        if (existingPerson != null) {
            updatedPatient.setPatientId(patientId);
            updatedPatient.setPersonId(existingPerson.getPersonId());
            patientDAO.updatePatient(updatedPatient);
            return Response.status(Response.Status.OK)
                    .entity("Updated the details of patient with ID " + patientId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
    }

    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("patientId") int patientId) {
        LOGGER.info("Deleting patient with ID: {}", patientId);
        patientDAO.deletePatient(patientId);
        return Response.ok().build();
    }
}
