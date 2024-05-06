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
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Billing;
import com.cw.validation.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/billings")
public class BillingResource {

    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);

    // Data Access Object for managing billings
    private BillingDAO billingDAO = new BillingDAO();

    // Retrieve all billings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Billing> getAllBillings() {
        LOGGER.info("Getting all billings.");
        return billingDAO.getAllBillings();
    }

    // Retrieve billing by ID
    @GET
    @Path("/{billingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingById(@PathParam("billingId") String billingIdParam) {
        // Validate and parse billing ID
        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");

        // Check if billing exists
        checkExistingBilling(billingId, "get");

        // Retrieve billing by ID
        LOGGER.info("Getting billing by ID: {}", billingId);
        Billing billing = billingDAO.getBillingById(billingId);

        return Response.ok(billing).build();
    }

    // Add a new billing
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        // Validate request body
        RequestErrorHandler.checkNullRequestBody(billing);
        // Validate and set patient
        billing.setPatient(RequestErrorHandler.validatePatientId(billing.getPatient()));

        // Add billing
        billingDAO.addBilling(billing);

        // Log success message
        LOGGER.info("Added new billing: {}", billing);

        return Response.status(Response.Status.CREATED)
                .entity("Billing successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Update an existing billing
    @PUT
    @Path("/{billingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("billingId") String billingIdParam, Billing updatedBilling) {
        // Validate and parse billing ID
        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");

        // Validate request body
        RequestErrorHandler.checkNullRequestBody(updatedBilling);
        // Check if billing exists
        checkExistingBilling(billingId, "update");
        // Validate and set patient
        updatedBilling.setPatient(RequestErrorHandler.validatePatientId(updatedBilling.getPatient()));

        // Update billing
        updatedBilling.setId(billingId);
        billingDAO.updateBilling(updatedBilling);

        // Log success message
        LOGGER.info("Updated billing with ID {}: {}", billingId, updatedBilling);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of billing with billing ID " + billingId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a billing by ID
    @DELETE
    @Path("/{billingId}")
    public Response deleteBilling(@PathParam("billingId") String billingIdParam) {
        // Validate and parse billing ID
        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");

        // Check if billing exists
        checkExistingBilling(billingId, "delete");

        // Delete billing
        billingDAO.deleteBilling(billingId);

        // Log success message
        LOGGER.info("Deleted billing with ID {}", billingId);

        return Response.status(Response.Status.OK)
                .entity("Deleted billing with billing ID " + billingId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a billing exists
    private void checkExistingBilling(int billingId, String methodName) throws EntityNotFoundException {
        Billing existingBilling = billingDAO.getBillingById(billingId);
        if (existingBilling == null) {
            // Log error message
            LOGGER.error("Billing with ID {} not found to {}", billingId, methodName);
            throw new EntityNotFoundException("Billing with billing ID " + billingId + " not found to " + methodName);
        }
    }
}
