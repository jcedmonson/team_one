package com.bah.msd.projectdataservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.bah.msd.projectdataservice.domain.Event;

public interface EventsRepository extends CrudRepository<Event, Long> {

}