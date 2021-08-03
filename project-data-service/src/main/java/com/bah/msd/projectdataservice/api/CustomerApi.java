package com.bah.msd.projectdataservice.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.bah.msd.projectdataservice.domain.Customer;
import com.bah.msd.projectdataservice.jwt.TokenRequestData;
import com.bah.msd.projectdataservice.jwt.TokenResponseData;
import com.bah.msd.projectdataservice.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

	@Autowired
	CustomersRepository repo;

	@GetMapping
	public Iterable<Customer> getAllCustomers() {
		return this.repo.findAll();
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") long id) {
		Optional<Customer> customer = repo.findById(id);

		if (customer.isPresent()) {
			return ResponseEntity.ok().body(customer);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
		if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getEmail() == null
				|| newCustomer.getPassword() == null) {
			return ResponseEntity.badRequest().build();
		}
		newCustomer = repo.save(newCustomer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCustomer.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();

		return response;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> checkCredentials(@RequestBody TokenRequestData request) {
		
		Optional<Customer> customer = repo.findByNameIgnoringCase(request.getName());
		
		TokenResponseData response = new TokenResponseData();
		
		if (customer.isPresent() && customer.get().getPassword().equals(request.getPassword())) {
			response.setScopes("com.bah.msd.projectdataservice");
			response.setAuthenticated(true);
		}

		return ResponseEntity.ok().body(response);		
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<?> putCustomer(@RequestBody Customer newCustomer, @PathVariable("customerId") long customerId) {
		if (newCustomer.getId() != customerId || newCustomer.getName() == null || newCustomer.getEmail() == null
				|| newCustomer.getPassword() == null) {
			return ResponseEntity.badRequest().build();
		}
		newCustomer = repo.save(newCustomer);

		return ResponseEntity.status(204).build();
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") long id) {
		Customer customer = repo.findById(id).orElse(null);
		if (customer == null) {

			return ResponseEntity.status(404).build();
		}

		repo.delete(customer);

		return ResponseEntity.status(204).build();
	}

}
