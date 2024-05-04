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
import com.cw.exception.InvalidIdFormatException;
import com.cw.exception.MissingRequestBodyException;
import com.cw.model.Person;
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
    public Response getPersonById(@PathParam("personId") int personId) {
        LOGGER.info("Retrieving person by ID: {}", personId);
        Person person = personDAO.getPersonById(personId);
        if (person != null) {
            return Response.ok(person).build();
        } else {
            throw new EntityNotFoundException("Person with ID " + personId + " not found.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        if (person == null) {
            throw new MissingRequestBodyException("Person information is missing");
        }
        LOGGER.info("Adding new person: {}", person);
        personDAO.addPerson(person);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("personId") String personIdParam, Person updatedPerson) {
        int personId;
        try {
            personId = Integer.parseInt(personIdParam);
        } catch (NumberFormatException e) {
            throw new InvalidIdFormatException("Invalid person Id format in the enpoint : " + personIdParam);
        }
        if (updatedPerson == null) {
            throw new MissingRequestBodyException("Person information is missing for person Id " + personId);
        }
        LOGGER.info("Updating person with ID {}", personId);
        Person existingPerson = personDAO.getPersonById(personId);

        if (existingPerson != null) {
            updatedPerson.setPersonId(personId);
            personDAO.updatePerson(updatedPerson);
            return Response.status(Response.Status.OK)
                    .entity("Updated the details of person with ID " + personId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        throw new EntityNotFoundException("Person with ID " + personId + " not found to update.");
    }

    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam("personId") int personId) {
        LOGGER.info("Deleting person with ID: {}", personId);
        personDAO.deletePerson(personId);
        return Response.ok().build();
    }
}
