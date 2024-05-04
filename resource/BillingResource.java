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
import com.cw.utils.RequestErrorHandler;
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
    public Response getBillingById(@PathParam("billingId") String billingIdParam) {
        
        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");
        checkExistingBilling(billingId, "get");
        
        LOGGER.info("Getting billing by billing Id: {}", billingId);
        Billing billing = billingDAO.getBillingById(billingId);

        return Response.ok(billing).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {

        RequestErrorHandler.checkNullRequestBody(billing);
        RequestErrorHandler.validatePatientId(billing.getPatient());

        billingDAO.addBilling(billing);
        return Response.status(Response.Status.CREATED)
        .entity("Billing successfully added to the database.")
        .type(MediaType.TEXT_PLAIN)
        .build();

    }

    @PUT
    @Path("/{billingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("billingId") String billingIdParam, Billing updatedBilling) {

        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");
        RequestErrorHandler.checkNullRequestBody(updatedBilling);
        checkExistingBilling(billingId, "update");
        RequestErrorHandler.validatePatientId(updatedBilling.getPatient());

        updatedBilling.setId(billingId);
        billingDAO.updateBilling(updatedBilling);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of billing with billing Id " + billingId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{billingId}")
    public Response deleteBilling(@PathParam("billingId") String billingIdParam) {

        int billingId = RequestErrorHandler.validateIdParam(billingIdParam, "billing");
        checkExistingBilling(billingId, "delete");

        billingDAO.deleteBilling(billingId);
        return Response.ok().build();
    }

    private void checkExistingBilling(int billingId, String methodName) throws EntityNotFoundException {
        Billing existingAppointment = billingDAO.getBillingById(billingId);
        if (existingAppointment == null) {
            throw new EntityNotFoundException("Appointment with appointment Id " + billingId + " not found to " + methodName);
        }
    }
}
