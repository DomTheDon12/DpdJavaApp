package com.dpd.DpdJavaApp.repository;

import com.dpd.DpdJavaApp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByNameContaining(String name);
}
