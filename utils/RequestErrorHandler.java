/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.utils;

/**
 *
 * @author daves
 */

import com.cw.exception.MissingRequestBodyException;

public class RequestErrorHandler {

    public static void checkNullRequestBody(Object obj) throws MissingRequestBodyException {
        if (obj == null) {
            throw new MissingRequestBodyException("Information is missing in the request body to perform this method");          
        }
    }

}
