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
import com.cw.dao.DoctorDAO;
import com.cw.dao.PatientDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.InvalidIdFormatException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Appointment;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/appointments")
public class AppointmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentResource.class);

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("appointmentId") int appointmentId) {
        LOGGER.info("Getting appointment by appointment Id: {}", appointmentId);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
        if (appointment != null) {
            return Response.ok(appointment).build();
        } else {
            throw new EntityNotFoundException("Appointment with ID " + appointmentId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new MissingRequestBodyException("Appointment information is missing");
        }
        int doctorId = appointment.getDoctor().getDoctorId();
        int patientId = appointment.getPatient().getPatientId();

        if (doctorId == 0) {
            throw new MissingRequestBodyException("Doctor id is missing");
        }
        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient id is missing");
        }

        if (!DoctorDAO.doctorIsExist(doctorId)) {
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found.");
        }

        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }

        appointmentDAO.addAppointment(appointment);
        return Response.status(Response.Status.CREATED).build();

    }

    @PUT
    @Path("/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") String appointmentIdParam, Appointment updatedAppointment) {

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(appointmentIdParam);
        } catch (NumberFormatException e) {
            throw new InvalidIdFormatException("Invalid appointment Id format in the enpoint : " + appointmentIdParam);
        }
        if (updatedAppointment == null) {
            throw new MissingRequestBodyException("Appointment information is missing to update appointment Id " + appointmentId);
        }

        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointmentId);

        if (existingAppointment == null) {
            throw new EntityNotFoundException("Appointment with appointment Id " + appointmentId + " not found to update.");
        }

        int doctorId = updatedAppointment.getDoctor().getDoctorId();
        int patientId = updatedAppointment.getPatient().getPatientId();

        if (doctorId == 0) {
            throw new MissingRequestBodyException("Doctor id is missing");
        }
        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient id is missing");
        }

        if (!DoctorDAO.doctorIsExist(doctorId)) {
            throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found.");
        }

        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }

        updatedAppointment.setId(appointmentId);
        appointmentDAO.updateAppointment(updatedAppointment);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of appointment with appointment Id " + appointmentId)
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @DELETE
    @Path("/{appointmentId}")
    public Response deleteAppointment(@PathParam("appointmentId") int appointmentId) {
        appointmentDAO.deleteAppointment(appointmentId);
        return Response.ok().build();
    }
}
