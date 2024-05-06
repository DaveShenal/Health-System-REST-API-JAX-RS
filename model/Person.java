/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.model;

import com.cw.validation.Validator;

/**
 *
 * @author daves
 */
public class Person implements Validator{

    private int personId;
    private String name;
    private String contactInformation;
    private String address;

    // Constructors
    public Person() {
    }

    public Person(int personId, String name, String contactInformation, String address) {
        this.personId = personId;
        this.name = name;
        this.contactInformation = contactInformation;
        this.address = address;
    }

    // Getters and setters
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean areAllFieldsFilled() {
        return name != null && !name.isEmpty() && contactInformation != null && !contactInformation.isEmpty() && address != null && !address.isEmpty();
    }
}
