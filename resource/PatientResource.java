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
import com.cw.utils.RequestErrorHandler;
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
    public Response getPatientById(@PathParam("patientId") String patientIdParam) {

        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");
        checkExistingPatient(patientId, "get");

        LOGGER.info("Getting patient by patient Id: {}", patientId);
        Patient patient = patientDAO.getPatientById(patientId);

        return Response.ok(patient).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {

        RequestErrorHandler.checkNullRequestBody(patient);

        LOGGER.info("Adding new patient: {}", patient);
        patientDAO.addPatient(patient);
        return Response.status(Response.Status.CREATED)
                .entity("Patient successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @PUT
    @Path("/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("patientId") String patientIdParam, Patient updatedPatient) {

        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");
        RequestErrorHandler.checkNullRequestBody(updatedPatient);
        checkExistingPatient(patientId, "update");

        updatedPatient.setPatientId(patientId);
        patientDAO.updatePatient(updatedPatient);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of patient with patient Id " + patientId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{patientId}")
    public Response deletePatient(@PathParam("patientId") String patientIdParam) {

        int patientId = RequestErrorHandler.validateIdParam(patientIdParam, "patient");
        checkExistingPatient(patientId, "delete");

        LOGGER.info("Deleting patient with ID: {}", patientId);
        patientDAO.deletePatient(patientId);
        return Response.status(Response.Status.OK)
                .entity("Deleted patient with patient Id " + patientId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    private void checkExistingPatient(int patientId, String methodName) throws EntityNotFoundException {
        Patient existingPatient = patientDAO.getPatientById(patientId);
        if (existingPatient == null) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found to " + methodName);
        }
    }
}
