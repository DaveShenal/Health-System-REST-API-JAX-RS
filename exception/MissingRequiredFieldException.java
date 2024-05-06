/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;

/**
 *
 * @author daves
 */
public class MissingRequiredFieldException extends RuntimeException {
    public MissingRequiredFieldException(String fieldName) {
        super("Required field '" + fieldName + "' is missing in the request payload");
    }
}
