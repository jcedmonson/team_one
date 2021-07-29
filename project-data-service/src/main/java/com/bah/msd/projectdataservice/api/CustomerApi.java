package com.bah.msd.projectdataservice.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.projectdataservice.domain.Customer;
//import com.bah.msd.projectdataservice.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

//	@Autowired
//	CustomersRepository repo;

	public CustomerApi() {}
	
	@GetMapping
	public Iterable<Customer> getAll() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		customers.add(new Customer(100, "Brian", "pass", "brian@a.com"));
		customers.add(new Customer(101, "Scott", "pass", "scott@a.com"));
		customers.add(new Customer(102, "Jeff", "pass", "jeff@a.com"));

		return customers;
		//return repo.findAll();
	}
//
//	@GetMapping("/{customerId}")
//	public Optional<Customer> getCustomerById(@PathVariable("customerId") long id) {
//		return repo.findById(id);
//	}
//
//	@PostMapping
//	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
//		if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getEmail() == null) {
//
//			return ResponseEntity.badRequest().build();
//		}
//
//		newCustomer = repo.save(newCustomer);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(newCustomer.getId()).toUri();
//
//		ResponseEntity<?> response = ResponseEntity.created(location).build();
//		return response;
//	}
//
//	@PutMapping("/{customerId}")
//	public ResponseEntity<?> updateCustomer(@RequestBody Customer newCustomer,
//			@PathVariable("customerId") long customerId) {
//
//		if (newCustomer.getId() != customerId || newCustomer.getName() == null || newCustomer.getEmail() == null) {
//
//			return ResponseEntity.badRequest().build();
//		}
//		
//		newCustomer = repo.save(newCustomer);
//		return ResponseEntity.ok().build();
//	}
//	
//	@DeleteMapping("/{customerId}")
//	public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") long id) {
//		Customer customer = repo.findById(id).orElse(null);
//		if (customer == null) {
//			
//			return ResponseEntity.badRequest().build();
//		}
//		
//		repo.delete(customer);
//
//		return ResponseEntity.ok().build();
//	}
}
