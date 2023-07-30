package com.dpd.DpdJavaApp;

import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.model.PhoneNumber;
import com.dpd.DpdJavaApp.model.response.SavePersonResponse;
import com.dpd.DpdJavaApp.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTests {
    private Person person;

    private Set<PhoneNumber> phoneNumbers = new HashSet<>();
    private ValidationService validationService = new ValidationService();

    @BeforeEach
    public void setup() {
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
    }

    @Test
    public void givenInvalidEmailWhenValidationThenReturnExceptionResponse() {
        String invalidEmail = "invalidemail";
        person.setEmail(invalidEmail);
        ResponseEntity<SavePersonResponse> response = validationService.validatePersonFields(person);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
    }

    @Test
    public void givenValidFieldsWhenValidationThenReturnNullResponse() {
        ResponseEntity<SavePersonResponse> response = validationService.validatePersonFields(person);
        assertThat(response).isNull();
    }

    @Test
    public void givenInvalidTajWhenValidationThenReturnExceptionResponse() {
        String invalidTaj = "1";
        person.setEmail(invalidTaj);
        ResponseEntity<SavePersonResponse> response = validationService.validatePersonFields(person);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
    }

    @Test
    public void givenInvalidTaxIDWhenValidationThenReturnExceptionResponse() {
        String invalidTaxID = "1";
        person.setTaxID(invalidTaxID);
        ResponseEntity<SavePersonResponse> response = validationService.validatePersonFields(person);
        assertThat(response.getBody().getExceptionThrown()).isTrue();
    }
}
