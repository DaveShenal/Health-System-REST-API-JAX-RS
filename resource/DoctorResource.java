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
import com.cw.utils.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/doctors")
public class DoctorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorResource.class);

    private DoctorDAO doctorDAO = new DoctorDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> getAllDoctors() {
        LOGGER.info("Retrieving all doctors.");
        return doctorDAO.getAllDoctors();
    }

    @GET
    @Path("/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorById(@PathParam("doctorId") String doctorIdParam) {

        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");
        checkExistingDoctor(doctorId, "get");

        LOGGER.info("Getting doctor by doctor Id: {}", doctorId);
        Doctor doctor = doctorDAO.getDoctorById(doctorId);

        return Response.ok(doctor).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        RequestErrorHandler.checkNullRequestBody(doctor);

        LOGGER.info("Adding new doctor: {}", doctor);
        doctorDAO.addDoctor(doctor);
        return Response.status(Response.Status.CREATED)
                .entity("Doctor successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("doctorId") String doctorIdParam, Doctor updatedDoctor) {

        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");
        RequestErrorHandler.checkNullRequestBody(updatedDoctor);
        checkExistingDoctor(doctorId, "update");

        updatedDoctor.setDoctorId(doctorId);
        doctorDAO.updateDoctor(updatedDoctor);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of doctor with doctor Id " + doctorId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") String doctorIdParam) {

        int doctorId = RequestErrorHandler.validateIdParam(doctorIdParam, "doctor");
        checkExistingDoctor(doctorId, "delete");

        LOGGER.info("Deleting doctor with ID: {}", doctorId);
        doctorDAO.deleteDoctor(doctorId);
        return Response.ok().build();
    }

    private void checkExistingDoctor(int doctorId, String methodName) throws EntityNotFoundException {
        Doctor existingDoctor = doctorDAO.getDoctorById(doctorId);
        if (existingDoctor == null) {
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found to " + methodName);
        }
    }
}
