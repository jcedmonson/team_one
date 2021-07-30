package com.bah.msd.projectdataservice.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bah.msd.projectdataservice.domain.Event;
import com.bah.msd.projectdataservice.repository.EventsRepository;

@RestController
@RequestMapping("/events")
public class EventsApi {

	@Autowired
	EventsRepository repo;

	@GetMapping
	public Collection<Event> getAllEvents() {
		return this.repo.getEventList();
	}
}
