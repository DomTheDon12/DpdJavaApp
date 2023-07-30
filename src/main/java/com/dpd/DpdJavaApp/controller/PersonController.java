package com.dpd.DpdJavaApp.controller;

import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.model.response.FindPersonResponse;
import com.dpd.DpdJavaApp.model.response.FindPersonsResponse;
import com.dpd.DpdJavaApp.model.response.PersonResponse;
import com.dpd.DpdJavaApp.model.response.SavePersonResponse;
import com.dpd.DpdJavaApp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dpd/person")
public class PersonController {

    private final PersonService personService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/save", consumes = {"application/json"})
    public ResponseEntity<SavePersonResponse> savePersonWithAddressAndPhoneNumber(@RequestBody Person person) {
        LOGGER.info("Save person operation started");
        ResponseEntity<SavePersonResponse> savePersonResponse = personService.savePerson(person);
        LOGGER.info("Save person operation ended");
        return savePersonResponse;
    }

    @GetMapping("/find/all")
    public ResponseEntity<FindPersonsResponse> findAllStudents() {
        LOGGER.info("Find all persons operation started");
        ResponseEntity<FindPersonsResponse> findPersonsResponse = personService.findAllPersons();
        LOGGER.info("Find all persons operation ended");
        return findPersonsResponse;
    }

    @GetMapping("/find/id/{personId}")
    public ResponseEntity<FindPersonResponse> findPersonById(@PathVariable Long personId) {
        LOGGER.info("Find person by id operation started");
        ResponseEntity<FindPersonResponse> findPersonResponse = personService.findPersonById(personId);
        LOGGER.info("Find person by id operation ended");
        return findPersonResponse;
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<FindPersonsResponse> findPersonContainingByName(@PathVariable String name) {
        LOGGER.info("Find persons by name operation started");
        ResponseEntity<FindPersonsResponse> findPersonsResponse = personService.findPersonsContainingByName(name);
        LOGGER.info("Find persons by name operation ended");
        return findPersonsResponse;
    }

    @DeleteMapping("/delete/{personId}")
    public ResponseEntity<PersonResponse> deletePersonById(@PathVariable Long personId) {
        LOGGER.info("Delete person by id operation started");
        ResponseEntity<PersonResponse> personResponse = personService.deletePersonById(personId);
        LOGGER.info("Delete person by id operation ended");
        return personResponse;
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<PersonResponse> deleteAllPerson() {
        LOGGER.info("Delete all persons from database operation started");
        ResponseEntity<PersonResponse> personResponse = personService.deleteAllPerson();
        LOGGER.info("Delete all persons from database operation ended");
        return personResponse;
    }
}
