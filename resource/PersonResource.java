/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.resource;

/**
 *
 * @author daves
 */
import com.cw.dao.PersonDAO;
import com.cw.exception.EntityNotFoundException;
import com.cw.model.Person;
import com.cw.utils.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/persons")
public class PersonResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    private PersonDAO personDAO = new PersonDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        LOGGER.info("Retrieving all persons.");
        return personDAO.getAllPersons();
    }

    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("personId") String personIdParam) {

        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");
        checkExistingPerson(personId, "get");

        LOGGER.info("Getting person by person Id: {}", personId);
        Person person = personDAO.getPersonById(personId);

        return Response.ok(person).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {

        RequestErrorHandler.checkNullRequestBody(person);

        LOGGER.info("Adding new person: {}", person);
        personDAO.addPerson(person);
        return Response.status(Response.Status.CREATED)
                .entity("Person successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("personId") String personIdParam, Person updatedPerson) {

        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");

        RequestErrorHandler.checkNullRequestBody(updatedPerson);

        checkExistingPerson(personId, "update");

        updatedPerson.setPersonId(personId);
        personDAO.updatePerson(updatedPerson);
        return Response.status(Response.Status.OK)
                .entity("Updated the details of person with ID " + personId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam("personId") String personIdParam) {

        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");
        checkExistingPerson(personId, "delete");

        LOGGER.info("Deleting person with ID: {}", personId);
        personDAO.deletePerson(personId);
        return Response.ok().build();
    }

    private void checkExistingPerson(int personId, String methodName) throws EntityNotFoundException {
        Person existingPerson = personDAO.getPersonById(personId);
        if (existingPerson == null) {
            throw new EntityNotFoundException("Person with ID " + personId + " not found to " + methodName);
        }
    }
}
