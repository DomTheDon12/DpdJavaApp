package com.dpd.DpdJavaApp.model.response;

import com.dpd.DpdJavaApp.model.Person;

public class FindPersonResponse extends PersonResponse {
    private final Person person;

    public FindPersonResponse(String message, boolean exceptionThrown, Person person) {
        super(message, exceptionThrown);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
