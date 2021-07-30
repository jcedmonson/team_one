package com.bah.msd.projectdataservice.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.projectdataservice.domain.Customer;
import com.bah.msd.projectdataservice.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

	@Autowired
	CustomersRepository repo;

	@GetMapping
	public Collection<Customer> getAllCustomers() {
		return this.repo.getCustomerList();
	}
}
