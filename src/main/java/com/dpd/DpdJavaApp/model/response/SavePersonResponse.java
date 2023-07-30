package com.dpd.DpdJavaApp.model.response;

import com.dpd.DpdJavaApp.model.Person;

public class SavePersonResponse extends PersonResponse {
    private final Person person;

    public SavePersonResponse(String message, boolean exceptionThrown, Person person) {
        super(message, exceptionThrown);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
