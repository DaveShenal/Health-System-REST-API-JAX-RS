/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;

/**
 *
 * @author daves
 */
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InternalServerErrorMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalServerErrorMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        // Log the exception for debugging purposes
        LOGGER.error("InternalServerError caught: {}", exception.getMessage(), exception);

        // Return a custom error response with HTTP status code 500
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal Server Error : " + exception.getMessage())
                .type("text/plain")
                .build();
    }
}
