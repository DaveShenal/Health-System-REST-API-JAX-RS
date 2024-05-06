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
import com.cw.validation.RequestErrorHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/persons")
public class PersonResource {
    
    // Logger for logging information
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    // Data Access Object for managing persons
    private PersonDAO personDAO = new PersonDAO();

    // Retrieve all persons
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        int n1 = 0;
        int n2 = 10;
        int n3 = n2/n1;
        LOGGER.info("Retrieving all persons.");
        return personDAO.getAllPersons();
    }

    // Retrieve person by ID
    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("personId") String personIdParam) {
        // Validate and parse person ID
        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");

        // Check if person exists
        checkExistingPerson(personId, "get");

        // Retrieve person by ID
        LOGGER.info("Getting person by ID: {}", personId);
        Person person = personDAO.getPersonById(personId);

        return Response.ok(person).build();
    }

    // Add a new person
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        // Validate request body
        RequestErrorHandler.validateEntitiy(person, "Person");

        // Add person
        LOGGER.info("Adding new person: {}", person);
        personDAO.addPerson(person);

        return Response.status(Response.Status.CREATED)
                .entity("Person successfully added to the database.")
                .type(MediaType.TEXT_PLAIN)
                .build();

    }

    // Update an existing person
    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("personId") String personIdParam, Person updatedPerson) {
        // Validate and parse person ID
        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");

        // Validate request body
        RequestErrorHandler.validateEntitiy(updatedPerson, "Person");
        // Check if person exists
        checkExistingPerson(personId, "update");

        // Update person
        updatedPerson.setPersonId(personId);
        personDAO.updatePerson(updatedPerson);

        // Log success message
        LOGGER.info("Updated person with ID {}: {}", personId, updatedPerson);

        return Response.status(Response.Status.OK)
                .entity("Updated the details of person with ID " + personId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Delete a person by ID
    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam("personId") String personIdParam) {
        // Validate and parse person ID
        int personId = RequestErrorHandler.validateIdParam(personIdParam, "person");

        // Check if person exists
        checkExistingPerson(personId, "delete");

        // Delete person
        LOGGER.info("Deleting person with ID: {}", personId);
        personDAO.deletePerson(personId);

        return Response.status(Response.Status.OK)
                .entity("Deleted person with ID " + personId)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    // Helper method to check if a person exists
    private void checkExistingPerson(int personId, String methodName) throws EntityNotFoundException {
        Person existingPerson = personDAO.getPersonById(personId);
        if (existingPerson == null) {
            // Log error message
            LOGGER.error("Person with ID {} not found to {}", personId, methodName);
            throw new EntityNotFoundException("Person with ID " + personId + " not found to " + methodName);
        }
    }
}
