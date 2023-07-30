package com.dpd.DpdJavaApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "persons")
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthPlace", nullable = false)
    private String birthPlace;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "TAJ", nullable = false)
    private String TAJ;

    @Column(name = "taxID", nullable = false)
    private String taxID;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "PERSON_ADDRESS_TABLE",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "address_id", referencedColumnName = "id")
            }
    )
    @JsonIgnoreProperties("persons")
    private Set<Address> addresses;

    @OneToMany(targetEntity = PhoneNumber.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_person_phone", referencedColumnName = "id")
    private Set<PhoneNumber> phoneNumbers;


    public Person(String name, String birthPlace, LocalDate birthDate, String TAJ, String taxID, String email, Set<Address> addresses, Set<PhoneNumber> phoneNumbers) {
        this.name = name;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.TAJ = TAJ;
        this.taxID = taxID;
        this.email = email;
        this.addresses = addresses;
        this.phoneNumbers = phoneNumbers;
    }

    public Person(Long id, String name, String birthPlace, LocalDate birthDate, String TAJ, String taxID, String email, Set<Address> addresses, Set<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.TAJ = TAJ;
        this.taxID = taxID;
        this.email = email;
        this.addresses = addresses;
        this.phoneNumbers = phoneNumbers;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
