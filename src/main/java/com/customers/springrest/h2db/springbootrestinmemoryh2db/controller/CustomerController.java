package com.customers.springrest.h2db.springbootrestinmemoryh2db.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.model.Customer;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.repo.CustomerRepository;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.service.CustomerService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("Get all Customers...");
		return customerService.findAllCustomersSortedByAgeInDescendingOrder();
	}

	@PostMapping("/customers/create")
	public Customer postCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
		System.out.println("Delete Customer with ID = " + id + "...");
		customerService.deleteById(id);
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete All Customers...");
		customerService.deleteAllCustomers();
		return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
	}

	@GetMapping("customers/age/{age}")
	public List<Customer> findByAge(@PathVariable int age) {
		List<Customer> customers = customerService.findByAge(age);
		return customers;
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");
		Customer updatedCustomer = customerService.updateCustomer(id, customer);
		if(updatedCustomer != null){
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
		System.out.println("Get Customer with ID = " + id );
		Optional<Customer> customerData = customerService.findById(id);
		if( customerData.isPresent()){
			Customer _customer = customerData.get();
			return new ResponseEntity<Customer>(_customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
