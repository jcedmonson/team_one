package com.bah.msd.projectdataservice.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.projectdataservice.domain.Registration;
import com.bah.msd.projectdataservice.repository.RegistrationsRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationApi {

	@Autowired
	RegistrationsRepository repo;

	@GetMapping
	public Collection<Registration> getAllRegistration() {
		return this.repo.getRegistrationList();
	}
}