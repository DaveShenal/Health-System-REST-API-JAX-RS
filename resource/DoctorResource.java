/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.DoctorDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Doctor;
import com.cw.validation.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/doctors")
public class DoctorResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorResource.class);

    // Data Access Object for managing doctors
    private DoctorDAO doctorDAO = new DoctorDAO();

    // Retrieve all doctors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> getAllDoctors() {
        LOGGER.info("Retrieving all doctors.");
        return doctorDAO.getAllDoctors();
    }

    // Retrieve doctor by ID
    @GET
    @Path("/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorById(@PathParam("doctorId") String doctorIdParam) {
        // Validate and parse doctor ID
        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");

        // Check if doctor exists
        checkExistingDoctor(doctorId, "get");

        // Retrieve doctor by ID
        LOGGER.info("Getting doctor by ID: {}", doctorId);
        Doctor doctor = doctorDAO.getDoctorById(doctorId);

        return Response.ok(doctor).build();
    }

    // Add a new doctor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        // Validate request body
        RequestErrorHandler.validateEntitiy(doctor,"Doctor");

        // Add doctor
        LOGGER.info("Adding new doctor: {}", doctor);
        doctorDAO.addDoctor(doctor);

        return Response.status(Response.Status.CREATED)
                .entity("Doctor successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    // Update an existing doctor
    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("doctorId") String doctorIdParam, Doctor updatedDoctor) {
        // Validate and parse doctor ID
        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");

        // Validate request body
        RequestErrorHandler.validateEntitiy(updatedDoctor,"Doctor");
        // Check if doctor exists
        checkExistingDoctor(doctorId, "update");

        // Update doctor
        updatedDoctor.setDoctorId(doctorId);
        doctorDAO.updateDoctor(updatedDoctor);

        // Log success message
        LOGGER.info("Updated doctor with ID {}: {}", doctorId, updatedDoctor);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of doctor with doctor ID " + doctorId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a doctor by ID
    @DELETE
    @Path("/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") String doctorIdParam) {
        // Validate and parse doctor ID
        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");

        // Check if doctor exists
        checkExistingDoctor(doctorId, "delete");

        // Delete doctor
        LOGGER.info("Deleting doctor with ID: {}", doctorId);
        doctorDAO.deleteDoctor(doctorId);

        return Response.status(Response.Status.OK)
                .entity("Deleted doctor with doctor ID " + doctorId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a doctor exists
    private void checkExistingDoctor(int doctorId, String methodName) throws EntityNotFoundException {
        Doctor existingDoctor = doctorDAO.getDoctorById(doctorId);
        if (existingDoctor == null) {
            // Log error message
            LOGGER.error("Doctor with ID {} not found to {}", doctorId, methodName);
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found to " + methodName);
        }
    }
}