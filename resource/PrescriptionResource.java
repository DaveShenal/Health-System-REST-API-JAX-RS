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
import com.cw.dao.PrescriptionDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Prescription;
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
    public Response getPrescriptionById(@PathParam("prescriptionId") int prescriptionId) {
        LOGGER.info("Getting prescription by prescription Id: {}", prescriptionId);
        Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionId);
        if (prescription != null) {
            return Response.ok(prescription).build();
        } else {
            throw new EntityNotFoundException("Prescription with ID " + prescriptionId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        if (prescription == null) {
            throw new MissingRequestBodyException("Prescription information is missing");
        }
        int patientId = prescription.getPatient().getPatientId();

        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient id is missing");
        }
        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }
        prescriptionDAO.addPrescription(prescription);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionId") int prescriptionId, Prescription updatedPrescription) {
        if (updatedPrescription == null) {
            throw new MissingRequestBodyException("Prescription information is missing for prescription Id " + prescriptionId);
        }
        Prescription existingPerson = prescriptionDAO.getPrescriptionById(prescriptionId);

        if (existingPerson != null) {
            updatedPrescription.setId(prescriptionId);
            prescriptionDAO.updatePrescription(updatedPrescription);
            return Response.status(Response.Status.OK)
                    .entity("Prescription with prescription Id " + prescriptionId + " not found to update.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Prescription with prescription Id " + prescriptionId + " not found to update.");
    }

    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@PathParam("prescriptionId") int prescriptionId) {
        prescriptionDAO.deletePrescription(prescriptionId);
        return Response.ok().build();
    }
}
