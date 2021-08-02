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

import com.bah.msd.projectdataservice.domain.Registration;
import com.bah.msd.projectdataservice.repository.RegistrationsRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationApi {

	@Autowired
	RegistrationsRepository repo;

	@GetMapping
	public Iterable<Registration> getAllRegistration() {
		return this.repo.findAll();
	}

	@GetMapping("/{registrationId}")
	public Optional<Registration> getRegistrationById(@PathVariable("registrationId") Long id) {
		return repo.findById(id);
	}

	@PostMapping
	public ResponseEntity<?> addRegistration(@RequestBody Registration newRegistration, UriComponentsBuilder uri) {
		if (newRegistration.getId() != 0 || newRegistration.getEvent_id() <= 0 || newRegistration.getCustomer_id() <= 0
				|| newRegistration.getRegistration_date() == null || newRegistration.getNotes() == null) {

			return ResponseEntity.badRequest().build();
		}

		newRegistration = repo.save(newRegistration);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newRegistration.getId()).toUri();

		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}

	@PutMapping("/{registrationId}")
	public ResponseEntity<?> updateRegistration(@RequestBody Registration newRegistration,
			@PathVariable("registrationId") long registrationId) {

		if (newRegistration.getId() != registrationId || newRegistration.getEvent_id() <= 0
				|| newRegistration.getCustomer_id() <= 0 || newRegistration.getRegistration_date() == null
				|| newRegistration.getNotes() == null) {

			return ResponseEntity.badRequest().build();
		}

		newRegistration = repo.save(newRegistration);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{registrationId}")
	public ResponseEntity<?> deleteRegistration(@PathVariable("registrationId") long id) {
		Registration registration = repo.findById(id).orElse(null);
		if (registration == null) {

			return ResponseEntity.badRequest().build();
		}

		repo.delete(registration);

		return ResponseEntity.ok().build();
	}
}
