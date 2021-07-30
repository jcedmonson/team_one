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

import com.bah.msd.projectdataservice.domain.Customer;

@Component
public class CustomersRepository {

	ArrayList<Customer> customerList = new ArrayList<Customer>();

	public CustomersRepository() {

		Customer c1 = new Customer(1, "Jacob", "pass", "j@b.com");
		Customer c2 = new Customer(2, "Bryan", "pass", "b@b.com");
		Customer c3 = new Customer(3, "Trevor", "pass", "t@b.com");
		Customer c4 = new Customer(4, "Elkin", "pass", "e@b.com");

		this.customerList.add(c1);
		this.customerList.add(c2);
		this.customerList.add(c3);
		this.customerList.add(c4);
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}
}