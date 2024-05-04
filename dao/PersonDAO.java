/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Person;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person(1, "John Doe", "john.doe@example.com", "123 Main St"));
        persons.add(new Person(2, "Jane Smith", "jane.smith@example.com", "456 Elm St"));
        persons.add(new Person(3, "Alice Johnson", "alice.johnson@example.com", "789 Oak St"));
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public Person getPersonById(int id) {
        for (Person person : persons) {
            if (person.getPersonId() == id) {
                return person;
            }
        }
        return null;
    }

    public void addPerson(Person person) {
        int nextId = getNextPersonId();
        person.setPersonId(nextId);
        persons.add(person);
    }

    public void updatePerson(Person updatedPerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getPersonId() == updatedPerson.getPersonId()) {
                persons.set(i, updatedPerson);
                System.out.println("Person updated: \n" + updatedPerson.toString());
                break;
            }
        }       
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getPersonId() == id);
    }

    public int getNextPersonId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Person person : persons) {
            if (person.getPersonId() > maxUserId) {
                maxUserId = person.getPersonId();
            }
        }
        return maxUserId + 1;
    }
}


