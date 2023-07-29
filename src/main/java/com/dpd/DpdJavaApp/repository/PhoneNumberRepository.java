package com.dpd.DpdJavaApp.repository;

import com.dpd.DpdJavaApp.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}
