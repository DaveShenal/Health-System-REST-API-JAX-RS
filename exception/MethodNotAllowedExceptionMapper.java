/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;

/**
 *
 * @author daves
 */
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MethodNotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodNotAllowedExceptionMapper.class);

    @Override
    public Response toResponse(NotAllowedException exception) {
        LOGGER.error("NotAllowedException caught: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("Method Not Allowed: " + exception.getMessage())
                .build();
    }
}

