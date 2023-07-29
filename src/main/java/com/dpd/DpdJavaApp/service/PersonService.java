package com.dpd.DpdJavaApp.service;

import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.repository.AddressRepository;
import com.dpd.DpdJavaApp.repository.PersonRepository;
import com.dpd.DpdJavaApp.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private AddressRepository addressRepository;

    private PhoneNumberRepository phoneNumberRepository;

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository, PhoneNumberRepository phoneNumberRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Person findPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findPersonsContainingByName(String name){
        return personRepository.findByNameContaining(name);
    }
}
