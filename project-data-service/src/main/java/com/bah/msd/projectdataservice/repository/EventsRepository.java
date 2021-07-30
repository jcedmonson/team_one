package com.bah.msd.projectdataservice.repository;

//import org.springframework.data.repository.CrudRepository;
//
//import com.bah.msd.projectdataservice.domain.Customer;
//
//public interface CustomersRepository extends CrudRepository<Customer, Long> {
//
//}

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.bah.msd.projectdataservice.domain.Event;

@Component
public class EventsRepository {

	ArrayList<Event> eventList = new ArrayList<Event>();

	public EventsRepository() {

		Event e1 = new Event(1, "CNF001", "All-Java Conference", "Lectures and exhibits covering all Java topics");
		Event e2 = new Event(2, "CNC001", "Rock Concert", "Full blown rock concert");
		Event e3 = new Event(3, "Trevor", "Cool New Event", "Wow, this is cool");

		this.eventList.add(e1);
		this.eventList.add(e2);
		this.eventList.add(e3);
	}

	public ArrayList<Event> getEventList() {
		return eventList;
	}

	public void setCustomerList(ArrayList<Event> eventList) {
		this.eventList = eventList;
	}
}