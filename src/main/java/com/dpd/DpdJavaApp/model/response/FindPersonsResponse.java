package com.dpd.DpdJavaApp.model.response;

import com.dpd.DpdJavaApp.model.Person;

import java.util.List;

public class FindPersonsResponse extends PersonResponse {
    private final List<Person> persons;

    public FindPersonsResponse(String message, boolean exceptionThrown, List<Person> persons) {
        super(message, exceptionThrown);
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
