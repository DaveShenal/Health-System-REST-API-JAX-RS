/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.exception;

/**
 *
 * @author daves
 */
public class MissingRequestBodyException extends RuntimeException {
    public MissingRequestBodyException(String message) {
        super(message);
    }
}
