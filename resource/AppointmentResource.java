/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.AppointmentDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.validation.RequestErrorHandler;
import com.cw.model.Appointment;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/appointments")
public class AppointmentResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentResource.class);

    // Data Access Object for managing appointments
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    // Retrieve all appointments
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAllAppointments() {
        LOGGER.info("Getting all appointments.");
        return appointmentDAO.getAllAppointments();
    }

    // Retrieve appointment by ID
    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("appointmentId") String appointmentIdParam) {
        // Validate and parse appointment ID
        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");

        // Check if appointment exists
        checkExistingAppointment(appointmentId, "get");

        // Retrieve appointment by ID
        LOGGER.info("Getting appointment by ID: {}", appointmentId);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        return Response.ok(appointment).build();
    }

    // Add a new appointment
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
        // Validate request body
        RequestErrorHandler.validateEntitiy(appointment, "Appointment");
        // Validate and set doctor and patient
        appointment.setDoctor(RequestErrorHandler.validateDoctorId(appointment.getDoctor()));
        appointment.setPatient(RequestErrorHandler.validatePatientId(appointment.getPatient()));

        // Add appointment
        appointmentDAO.addAppointment(appointment);

        // Log success message
        LOGGER.info("Added new appointment: {}", appointment);

        return Response.status(Response.Status.CREATED)
                .entity("Appointment successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Update an existing appointment
    @PUT
    @Path("/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") String appointmentIdParam, Appointment updatedAppointment) {
        // Validate and parse appointment ID
        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");

        // Validate request body
        RequestErrorHandler.validateEntitiy(updatedAppointment, "Appointment");
        // Check if appointment exists
        checkExistingAppointment(appointmentId, "update");
        // Validate and set doctor and patient
        updatedAppointment.setDoctor(RequestErrorHandler.validateDoctorId(updatedAppointment.getDoctor()));
        updatedAppointment.setPatient(RequestErrorHandler.validatePatientId(updatedAppointment.getPatient()));

        // Update appointment
        updatedAppointment.setId(appointmentId);
        appointmentDAO.updateAppointment(updatedAppointment);

        // Log success message
        LOGGER.info("Updated appointment with ID {}: {}", appointmentId, updatedAppointment);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of appointment with appointment ID " + appointmentId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete an appointment by ID
    @DELETE
    @Path("/{appointmentId}")
    public Response deleteAppointment(@PathParam("appointmentId") String appointmentIdParam) {
        // Validate and parse appointment ID
        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");

        // Check if appointment exists
        checkExistingAppointment(appointmentId, "delete");

        // Delete appointment
        appointmentDAO.deleteAppointment(appointmentId);

        // Log success message
        LOGGER.info("Deleted appointment with ID {}", appointmentId);

        return Response.status(Response.Status.OK)
                .entity("Deleted appointment with appointment ID " + appointmentId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if an appointment exists
    private void checkExistingAppointment(int appointmentId, String methodName) throws EntityNotFoundException {
        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointmentId);
        if (existingAppointment == null) {
            // Log error message
            LOGGER.error("Appointment with ID {} not found to {}", appointmentId, methodName);
            throw new EntityNotFoundException("Appointment with appointment ID " + appointmentId + " not found to " + methodName);
        }
    }
}
