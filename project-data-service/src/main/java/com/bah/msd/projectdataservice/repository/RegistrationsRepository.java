package com.bah.msd.projectdataservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.bah.msd.projectdataservice.domain.Registration;

public interface RegistrationsRepository extends CrudRepository<Registration, Long> {

}