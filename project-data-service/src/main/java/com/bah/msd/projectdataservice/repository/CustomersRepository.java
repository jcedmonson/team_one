package com.bah.msd.projectdataservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bah.msd.projectdataservice.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByNameIgnoringCase(String name);
}
