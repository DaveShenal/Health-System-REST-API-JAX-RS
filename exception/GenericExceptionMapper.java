/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;

/**
 *
 * @author daves
 */
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);


    @Override
    public Response toResponse(Throwable exception) {
        String errorMessage = "An unexpected error occurred. Please try again later.";
        // Log the exception for debugging purposes
        LOGGER.error("InvalidIdFormatException caught: {}", exception.getMessage(), exception);
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}

