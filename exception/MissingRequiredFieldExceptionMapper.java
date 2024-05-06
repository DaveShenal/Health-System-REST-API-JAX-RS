/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author daves
 */
// Exception mapper to handle MissingRequiredFieldException
@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MissingRequiredFieldExceptionMapper.class);
    
    @Override
    public Response toResponse(MissingRequiredFieldException exception) {
        LOGGER.error("MissingRequiredFieldException caught: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(exception.getMessage())
                       .type(MediaType.TEXT_PLAIN)
                       .build();
    }
}
