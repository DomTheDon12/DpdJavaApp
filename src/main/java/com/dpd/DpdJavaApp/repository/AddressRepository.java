package com.dpd.DpdJavaApp.repository;

import com.dpd.DpdJavaApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
