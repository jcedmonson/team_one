package com.bah.msd.projectdataservice.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.bah.msd.projectdataservice.domain.Registration;

@Component
public class RegistrationsRepository {

	ArrayList<Registration> registrationList = new ArrayList<Registration>();

	public RegistrationsRepository() {

		Registration r1 = new Registration(1, 1, 1, LocalDate.of(2019, 1, 1), "looking forward to the event");
		Registration r2 = new Registration(2, 1, 2, LocalDate.of(2019, 1, 5), "is parking available?");

		this.registrationList.add(r1);
		this.registrationList.add(r2);
	}

	public ArrayList<Registration> getRegistrationList() {
		return registrationList;
	}

	public void setRegistrationList(ArrayList<Registration> registrationList) {
		this.registrationList = registrationList;
	}
}
