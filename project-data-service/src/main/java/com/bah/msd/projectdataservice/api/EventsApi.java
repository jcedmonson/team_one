package com.bah.msd.projectdataservice.api;

import java.util.ArrayList;

import com.bah.msd.projectdataservice.domain.Event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsApi {

    ArrayList<Event> events = new ArrayList<Event>();
	
	public EventsApi() {
		events.add(new Event("Sample Name", "Sample Location"));

	}
	
	@GetMapping
	public Iterable<Event> getAll() {
		return events;
	}

}
