package com.bah.msd.projectdataservice.api;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.projectdataservice.domain.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public CustomerApi() {
		customers.add(new Customer(100, "Jacob", "pass", "j@b.com"));
		customers.add(new Customer(101, "Bryan", "pass", "b@b.com"));
		customers.add(new Customer(102, "Trevor", "pass", "t@b.com"));
		customers.add(new Customer(103, "Elkin", "pass", "e@b.com"));
	}
	
	@GetMapping
	public Iterable<Customer> getAll() {
		return customers;
	}

}
