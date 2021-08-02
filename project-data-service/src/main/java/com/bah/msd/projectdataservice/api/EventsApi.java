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

import com.bah.msd.projectdataservice.domain.Event;
import com.bah.msd.projectdataservice.repository.EventsRepository;

@RestController
@RequestMapping("/events")
public class EventsApi {

	@Autowired
	EventsRepository repo;

	@GetMapping
	public Iterable<Event> getAllEvents() {
		return this.repo.findAll();
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEventById(@PathVariable("eventId") Long id) {
		Optional<Event> event =  repo.findById(id);
		
		if (event.isPresent()) {
			return ResponseEntity.ok().body(event);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<?> addEvent(@RequestBody Event newEvent, UriComponentsBuilder uri) {
		if (newEvent.getId() != 0 || newEvent.getCode() == null || newEvent.getTitle() == null
				|| newEvent.getDescription() == null) {

			return ResponseEntity.badRequest().build();
		}

		newEvent = repo.save(newEvent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId())
				.toUri();

		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<?> updateEvent(@RequestBody Event newEvent, @PathVariable("eventId") long eventId) {

		if (newEvent.getId() != eventId || newEvent.getCode() == null || newEvent.getTitle() == null
				|| newEvent.getDescription() == null) {

			return ResponseEntity.badRequest().build();
		}

		newEvent = repo.save(newEvent);
		return ResponseEntity.status(204).build();
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteEvent(@PathVariable("eventId") long id) {
		Event event = repo.findById(id).orElse(null);
		if (event == null) {

			return ResponseEntity.status(404).build();
		}

		repo.delete(event);

		return ResponseEntity.status(204).build();
	}
}
