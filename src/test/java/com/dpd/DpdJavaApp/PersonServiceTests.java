package com.dpd.DpdJavaApp;

import com.dpd.DpdJavaApp.exception.FieldValidationException;
import com.dpd.DpdJavaApp.model.Address;
import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.model.PhoneNumber;
import com.dpd.DpdJavaApp.model.response.FindPersonResponse;
import com.dpd.DpdJavaApp.model.response.FindPersonsResponse;
import com.dpd.DpdJavaApp.model.response.PersonResponse;
import com.dpd.DpdJavaApp.model.response.SavePersonResponse;
import com.dpd.DpdJavaApp.repository.AddressRepository;
import com.dpd.DpdJavaApp.repository.PersonRepository;
import com.dpd.DpdJavaApp.service.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

import com.dpd.DpdJavaApp.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PersonServiceTests {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ValidationService validationService;

    private PersonService personService;

    private Person person;

    private Address address;

    private Set<PhoneNumber> phoneNumbers = new HashSet<>();
    private Set<Person> persons = new HashSet<>();
    private Set<Address> addresses = new HashSet<>();

    @BeforeEach
    public void setup() {
        personService = new PersonService(personRepository, addressRepository, validationService);
        phoneNumbers.add(PhoneNumber.builder().phoneNumber("06301234567").build());

        person = Person.builder()
                .id(1L)
                .name("Bilbo Baggins")
                .email("bilbo@gmail.com")
                .taxID("0123456789")
                .TAJ("012345678")
                .birthDate(LocalDate.of(1980, 1, 1))
                .birthPlace("Middle Earth")
                .phoneNumbers(phoneNumbers)
                .build();
        address = Address.builder().id(2L).city("asd").postalCode(5000).houseNumber(10).street("asd").build();
        persons.add(person);
        address.setPerson(persons);
        addresses.add(address);
        person.setAddresses(addresses);
    }

    @Test
    public void givenPersonObjectWhenSavePersonThenReturnOk() {
        given(personRepository.findById(person.getId())).willReturn(Optional.empty());

        given(personRepository.save(person)).willReturn(person);

        ResponseEntity<SavePersonResponse> response = personService.savePerson(person);

        assertThat(response.getBody().getPerson()).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void givenPersonObjectWithInvalidFieldsWhenSavePersonThenReturnBadRequest() {
        FieldValidationException fieldValidationException = new FieldValidationException("Field validation failed for the given email: " + person.getEmail());
        ResponseEntity<SavePersonResponse> expectedResponse = new ResponseEntity<>(new SavePersonResponse(fieldValidationException.getMessage(), true, null), HttpStatus.BAD_REQUEST);

        given(validationService.validatePersonFields(person)).willReturn(expectedResponse);

        ResponseEntity<SavePersonResponse> response = personService.savePerson(person);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void givenPersonObjectWhenSavePersonThenReturnInternalServerError() {
        given(personRepository.save(person)).willThrow(new RuntimeException());

        ResponseEntity<SavePersonResponse> response = personService.savePerson(person);

        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void whenFindAllPersonsThenReturnOk() {
        given(personRepository.findAll()).willReturn(new ArrayList<>(persons));
        ResponseEntity<FindPersonsResponse> response = personService.findAllPersons();
        assertThat(response.getBody().getPersons()).isNotEmpty();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenFindAllPersonsThenReturnNotFound() {
        given(personRepository.findAll()).willReturn(new ArrayList<>());
        ResponseEntity<FindPersonsResponse> response = personService.findAllPersons();
        assertThat(response.getBody().getPersons()).isNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void whenFindAllPersonsThenReturnInternalServerError() {
        given(personRepository.findAll()).willReturn(new ArrayList<>());
        given(personService.findAllPersons()).willThrow(new RuntimeException());
        ResponseEntity<FindPersonsResponse> response = personService.findAllPersons();
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void whenFindPersonByIdThenReturnOk() {
        given(personRepository.findById(1L)).willReturn(Optional.ofNullable(person));
        ResponseEntity<FindPersonResponse> response = personService.findPersonById(1L);
        assertThat(response.getBody().getPerson()).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenFindPersonByIdThenReturnNotFound() {
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        ResponseEntity<FindPersonResponse> response = personService.findPersonById(1L);
        assertThat(response.getBody().getPerson()).isNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void whenFindPersonByIdThenReturnInternalServerError() {
        given(personRepository.findById(1L)).willThrow(new RuntimeException());
        ResponseEntity<FindPersonResponse> response = personService.findPersonById(1L);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void whenFindPersonContainingNameThenReturnOk() {
        String name = "test";
        given(personRepository.findByNameContaining(name)).willReturn(new ArrayList<>(persons));
        ResponseEntity<FindPersonsResponse> response = personService.findPersonsContainingByName(name);
        assertThat(response.getBody().getPersons()).isNotEmpty();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenFindPersonContainingNameThenReturnNotFound() {
        String name = "test";
        given(personRepository.findByNameContaining(name)).willReturn(new ArrayList<>());
        ResponseEntity<FindPersonsResponse> response = personService.findPersonsContainingByName(name);
        assertThat(response.getBody().getPersons()).isNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void whenFindPersonContainingNameThenReturnInternalServerError() {
        String name = "test";
        given(personRepository.findByNameContaining(name)).willThrow(new RuntimeException());
        ResponseEntity<FindPersonsResponse> response = personService.findPersonsContainingByName(name);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void whenDeletePersonByIdThenReturnOk() {
        given(personRepository.existsById(1L)).willReturn(true);
        ResponseEntity<PersonResponse> response = personService.deletePersonById(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenDeletePersonByIdThenReturnNotFound() {
        given(personRepository.existsById(1L)).willReturn(false);
        ResponseEntity<PersonResponse> response = personService.deletePersonById(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void whenDeletePersonByIdThenReturnInternalServerError() {
        given(personRepository.existsById(1L)).willThrow(new RuntimeException());
        ResponseEntity<PersonResponse> response = personService.deletePersonById(1L);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void whenDeleteAllPersonThenReturnOk() {
        ResponseEntity<PersonResponse> response = personService.deleteAllPerson();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenDeleteAllPersonThenReturnInternalServerError() {
        doThrow(new RuntimeException()).when(personRepository).deleteAll();
        ResponseEntity<PersonResponse> response = personService.deleteAllPerson();
        assertThat(response.getBody().getExceptionThrown()).isTrue();
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

}
