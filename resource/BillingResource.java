/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.BillingDAO;
import com.cw.dao.DoctorDAO;
import com.cw.dao.PatientDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Billing;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/billings")
public class BillingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);

    private BillingDAO billingDAO = new BillingDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Billing> getAllBillings() {
        return billingDAO.getAllBillings();
    }

    @GET
    @Path("/{billingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingById(@PathParam("billingId") int billingId) {
        LOGGER.info("Getting billing by billing Id: {}", billingId);
        Billing billing = billingDAO.getBillingById(billingId);
        if (billing != null) {
            return Response.ok(billing).build();
        } else {
            throw new EntityNotFoundException("Billing with ID " + billingId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        if (billing == null) {
            throw new MissingRequestBodyException("Billing information is missing");
        }

        int patientId = billing.getPatient().getPatientId();

        if (patientId == 0) {
            throw new MissingRequestBodyException("Patient id is missing");
        }
        if (!PatientDAO.patientIsExist(patientId)) {
            throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
        }

        billingDAO.addBilling(billing);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{billingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("billingId") int billingId, Billing updatedBilling) {
        if (updatedBilling == null) {
            throw new MissingRequestBodyException("Billing information is missing for billing Id " + billingId);
        }
        Billing existingPerson = billingDAO.getBillingById(billingId);

        if (existingPerson != null) {
            updatedBilling.setId(billingId);
            billingDAO.updateBilling(updatedBilling);
            return Response.status(Response.Status.OK)
                    .entity("Updated the details of billing with billing Id " + billingId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Billing with billing Id " + billingId + " not found to update.");
    }

    @DELETE
    @Path("/{billingId}")
    public Response deleteBilling(@PathParam("billingId") int billingId) {
        billingDAO.deleteBilling(billingId);
        return Response.ok().build();
    }
}
