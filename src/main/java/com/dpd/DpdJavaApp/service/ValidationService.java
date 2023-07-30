package com.dpd.DpdJavaApp.service;

import com.dpd.DpdJavaApp.exception.FieldValidationException;
import com.dpd.DpdJavaApp.model.Person;
import com.dpd.DpdJavaApp.model.response.SavePersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    public ResponseEntity<SavePersonResponse> validatePersonFields(Person person) {
        if (!validateEmail(person.getEmail())) {
            return new ResponseEntity<>(new SavePersonResponse("Field validation failed for the given email: " + person.getEmail(), true, null), HttpStatus.BAD_REQUEST);
        } else if (!validateTaxId(person.getTaxID())) {
            return new ResponseEntity<>(new SavePersonResponse("Field validation failed for the given Taj number: " + person.getTAJ(), true, null), HttpStatus.BAD_REQUEST);
        } else if (!validateTajNumber(person.getTAJ())) {
            return new ResponseEntity<>(new SavePersonResponse("Field validation failed for the given tax ID: " + person.getTaxID(), true, null), HttpStatus.BAD_REQUEST);
        } else {
            return null;
        }

    }

    private boolean validateEmail(String email) {
        try {
            String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (Pattern.compile(emailRegexPattern).matcher(email).matches()) {
                return true;
            }
            throw new FieldValidationException("Field validation failed for the given email: " + email);
        } catch (FieldValidationException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return false;
        }
    }

    private boolean validateTajNumber(String taj) {
        try {
            String tajRegexPattern = "[\\d]{9}";
            if (Pattern.compile(tajRegexPattern).matcher(taj).matches()) {
                return true;
            }
            throw new FieldValidationException("Field validation failed for the given Taj number: " + taj);
        } catch (FieldValidationException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return false;
        }
    }

    private boolean validateTaxId(String taxID) {
        try {
            String taxIDPattern = "[\\d]{10}";
            if (Pattern.compile(taxIDPattern).matcher(taxID).matches()) {
                return true;
            }
            throw new FieldValidationException("Field validation failed for the given tax ID: " + taxID);
        } catch (FieldValidationException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return false;
        }
    }
}
