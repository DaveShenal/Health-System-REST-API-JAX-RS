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
import com.cw.dao.PersonDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Doctor;
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
    public Response getDoctorById(@PathParam("doctorId") int doctorId) {
        LOGGER.info("Getting doctor by ID: {}", doctorId);
        Doctor doctor = doctorDAO.getDoctorById(doctorId);
        if (doctor != null) {
            return Response.ok(doctor).build();
        } else {
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new MissingRequestBodyException("Doctor information is missing");
        }
        LOGGER.info("Adding new doctor: {}", doctor);
        doctorDAO.addDoctor(doctor);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("doctorId") int doctorId, Doctor updatedDoctor) {
        if (updatedDoctor == null) {
            throw new MissingRequestBodyException("Doctor information is missing for doctor Id " + doctorId);
        }
        LOGGER.info("Updating doctor with ID {}: {}", doctorId);
        Doctor existingPerson = doctorDAO.getDoctorById(doctorId);

        if (existingPerson != null) {
            updatedDoctor.setDoctorId(doctorId);
            updatedDoctor.setPersonId(existingPerson.getPersonId());
            doctorDAO.updateDoctor(updatedDoctor);
            return Response.status(Response.Status.OK)
                    .entity("Updated the details of doctor with ID " + doctorId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found to update.");
    }

    @DELETE
    @Path("/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") int doctorId) {
        LOGGER.info("Deleting doctor with ID: {}", doctorId);
        doctorDAO.deleteDoctor(doctorId);
        return Response.ok().build();
    }
}
