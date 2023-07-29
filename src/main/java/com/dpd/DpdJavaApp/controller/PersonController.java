package com.dpd.DpdJavaApp.controller;

import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dpd")
public class PersonController {

    private PersonService personService;

    public PersonController (PersonService personService){
        this.personService = personService;
    }

    @PostMapping("/person")
    public Person savePersonWithAddressAndPhoneNumber(@RequestBody Person person){
        return personService.savePerson(person);
    }

    @GetMapping("/person/all")
    public List<Person> findAllStudents(){
        return personService.findAllPersons();
    }

    @GetMapping("/person/id/{personId}")
    public Person findPersonById(@PathVariable Long personId){
        return personService.findPersonById(personId);
    }

    @GetMapping("/person/name/{name}")
    public List<Person> findPersonContainingByName(@PathVariable String name){
        return personService.findPersonsContainingByName(name);
    }
}
