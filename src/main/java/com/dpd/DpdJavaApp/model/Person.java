package com.dpd.DpdJavaApp.model;

import java.time.LocalDate;
import java.util.List;

public class Person {

    private String name;
    private String birthPlace;
    private LocalDate birthDate;
    private String TAJ;
    private String taxID;
    private String email;
    private List<Address> addresses;
    private List<String> phoneNumbers;

    public Person(String name, String birthPlace, LocalDate birthDate, String TAJ, String taxID, String email, List<Address> addresses, List<String> phoneNumbers) {
        this.name = name;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.TAJ = TAJ;
        this.taxID = taxID;
        this.email = email;
        this.addresses = addresses;
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getTAJ() {
        return TAJ;
    }

    public void setTAJ(String TAJ) {
        this.TAJ = TAJ;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
