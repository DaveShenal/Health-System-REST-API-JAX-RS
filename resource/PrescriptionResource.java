/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.PrescriptionDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Prescription;
import com.cw.utils.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/prescriptions")
public class PrescriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);

    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        return prescriptionDAO.getAllPrescriptions();
    }

    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionById(@PathParam("prescriptionId") String prescriptionIdParam) {

        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");
        checkExistingPrescription(prescriptionId, "get");

        LOGGER.info("Getting prescription by prescription Id: {}", prescriptionId);
        Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionId);

        return Response.ok(prescription).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {

        RequestErrorHandler.checkNullRequestBody(prescription);
        RequestErrorHandler.validatePatientId(prescription.getPatient());

        prescriptionDAO.addPrescription(prescription);
        return Response.status(Response.Status.CREATED)
                .entity("Prescription successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionId") String prescriptionIdParam, Prescription updatedPrescription) {

        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");
        RequestErrorHandler.checkNullRequestBody(updatedPrescription);
        checkExistingPrescription(prescriptionId, "update");
        RequestErrorHandler.validatePatientId(updatedPrescription.getPatient());

        updatedPrescription.setId(prescriptionId);
        prescriptionDAO.updatePrescription(updatedPrescription);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of prescription with prescription Id " + prescriptionId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@PathParam("prescriptionId") String prescriptionIdParam) {

        int prescriptionId = RequestErrorHandler.validateIdParam(prescriptionIdParam, "prescription");
        checkExistingPrescription(prescriptionId, "delete");

        LOGGER.info("Deleting prescription with ID: {}", prescriptionId);
        prescriptionDAO.deletePrescription(prescriptionId);
        return Response.status(Response.Status.OK)
                .entity("Deleted prescription with prescription Id " + prescriptionId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    private void checkExistingPrescription(int prescriptionId, String methodName) throws EntityNotFoundException {
        Prescription existingPrescription = prescriptionDAO.getPrescriptionById(prescriptionId);
        if (existingPrescription == null) {
            throw new EntityNotFoundException("Prescription with ID " + prescriptionId + " not found to " + methodName);
        }
    }
}
