package com.dpd.DpdJavaApp.service;

import com.dpd.DpdJavaApp.model.Address;
import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.model.response.FindPersonResponse;
import com.dpd.DpdJavaApp.model.response.FindPersonsResponse;
import com.dpd.DpdJavaApp.model.response.PersonResponse;
import com.dpd.DpdJavaApp.model.response.SavePersonResponse;
import com.dpd.DpdJavaApp.repository.AddressRepository;
import com.dpd.DpdJavaApp.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;


    private final ValidationService validationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public PersonService(PersonRepository personRepository, AddressRepository addressRepository, ValidationService validationService) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.validationService = validationService;
    }

    public ResponseEntity<SavePersonResponse> savePerson(Person person) {
        try {
            List<Address> addresses = addressRepository.findAll();
            for(Address incomingAddress: person.getAddresses()){
                if(addresses.contains(incomingAddress)){
                    Long id = addresses.stream().filter( savedAddress -> savedAddress.equals(incomingAddress)).findAny().get().getId();
                    incomingAddress.setId(id);
                } else{
                    addressRepository.save(incomingAddress);
                }
            }
            ResponseEntity<SavePersonResponse> validationResponse = validationService.validatePersonFields(person);
            if (validationResponse == null) {
                Person savedPerson = personRepository.save(person);
                return new ResponseEntity<>(new SavePersonResponse("Person saved successfully", false, savedPerson), HttpStatus.OK);
            }
            return validationResponse;
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new SavePersonResponse(exception.getMessage(), true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<FindPersonsResponse> findAllPersons() {
        try {
            List<Person> persons = personRepository.findAll();
            if (persons.isEmpty()) {
                return new ResponseEntity<>(new FindPersonsResponse("No person were found", false, null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new FindPersonsResponse("The search was successful", false, persons), HttpStatus.OK);
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new FindPersonsResponse(exception.getMessage(), true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<FindPersonResponse> findPersonById(Long id) {
        try {
            Person person = personRepository.findById(id).orElse(null);
            if (person != null) {
                return new ResponseEntity<>(new FindPersonResponse("Person was successfully found with id: " + id, false, person), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new FindPersonResponse("No person were found with the given id" + id, false, null), HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new FindPersonResponse(exception.getMessage(), true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<FindPersonsResponse> findPersonsContainingByName(String name) {
        try {
            List<Person> persons = personRepository.findByNameContaining(name);
            if (persons.isEmpty()) {
                return new ResponseEntity<>(new FindPersonsResponse("No person were found with the given name: " + name, false, null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new FindPersonsResponse("Persons were successfully found with name: " + name, false, persons), HttpStatus.OK);
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new FindPersonsResponse(exception.getMessage(), true, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<PersonResponse> deletePersonById(Long id) {
        try {
            if (personRepository.existsById(id)) {
                personRepository.deleteById(id);
                return new ResponseEntity<>(new PersonResponse("Person was deleted succesfully by id: " + id, false), HttpStatus.OK);
            }
            return new ResponseEntity<>(new PersonResponse("No person was found by id: " + id, true), HttpStatus.NOT_FOUND);
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new PersonResponse(exception.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<PersonResponse> deleteAllPerson() {
        try {
            personRepository.deleteAll();
            return new ResponseEntity<>(new PersonResponse("All persons were deleted from the database", false), HttpStatus.OK);
        } catch (RuntimeException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new ResponseEntity<>(new PersonResponse(exception.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
