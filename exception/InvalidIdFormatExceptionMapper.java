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
public class InvalidIdFormatExceptionMapper implements ExceptionMapper<InvalidIdFormatException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidIdFormatExceptionMapper.class);

    @Override
    public Response toResponse(InvalidIdFormatException exception) {
        LOGGER.error("InvalidIdFormatException caught: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
