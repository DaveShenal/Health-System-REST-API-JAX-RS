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
import com.cw.utils.RequestErrorHandler;
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
    public Response getAppointmentById(@PathParam("appointmentId") String appointmentIdParam) {

        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");
        checkExistingAppoinment(appointmentId, "get");

        LOGGER.info("Getting appointment by appointment Id: {}", appointmentId);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        return Response.ok(appointment).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {

        RequestErrorHandler.checkNullRequestBody(appointment);
        RequestErrorHandler.validateDoctorId(appointment.getDoctor());
        RequestErrorHandler.validatePatientId(appointment.getPatient());

        appointmentDAO.addAppointment(appointment);
        return Response.status(Response.Status.CREATED)
                .entity("Billing successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @PUT
    @Path("/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") String appointmentIdParam, Appointment updatedAppointment) {

        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");

        RequestErrorHandler.checkNullRequestBody(updatedAppointment);
        checkExistingAppoinment(appointmentId, "update");
        RequestErrorHandler.validateDoctorId(updatedAppointment.getDoctor());
        RequestErrorHandler.validatePatientId(updatedAppointment.getPatient());

        updatedAppointment.setId(appointmentId);
        appointmentDAO.updateAppointment(updatedAppointment);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of appointment with appointment Id " + appointmentId)
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @DELETE
    @Path("/{appointmentId}")
    public Response deleteAppointment(@PathParam("appointmentId") String appointmentIdParam) {

        int appointmentId = RequestErrorHandler.validateIdParam(appointmentIdParam, "appointment");

        checkExistingAppoinment(appointmentId, "delete");

        appointmentDAO.deleteAppointment(appointmentId);
        return Response.ok().build();
    }

    private void checkExistingAppoinment(int appointmentId, String methodName) throws EntityNotFoundException {
        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointmentId);
        if (existingAppointment == null) {
            throw new EntityNotFoundException("Appointment with appointment Id " + appointmentId + " not found to " + methodName);
        }
    }

}
