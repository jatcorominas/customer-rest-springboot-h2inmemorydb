package com.customers.springrest.h2db.springbootrestinmemoryh2db.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.model.Customer;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.repo.CustomerRepository;



@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository repository;
	
	public List<Customer> findAllCustomers(){
		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);
		return customers;
		
	}
	
	public List<Customer> findAllCustomersSortedByAgeInDescendingOrder(){
		List<Customer> customersSortedByAgeInDescOrder = new ArrayList<>();
		repository.findAllByOrderByAgeDesc().forEach(customersSortedByAgeInDescOrder::add);
		return customersSortedByAgeInDescOrder;
	}
	
	public Customer saveCustomer(Customer customer){
		Customer _customer = repository.save(new Customer(customer.getName(), customer.getAge()));
		return _customer;
	}
	
	public void deleteById(Long id){
		repository.deleteById(id);
	}
	
	public void deleteAllCustomers(){
		repository.deleteAll();
	}
	
	public List<Customer> findByAge(int age){
		return repository.findByAge(age);
	}
	
	public Optional<Customer> findById(Long id){
		Optional<Customer> customerData = repository.findById(id);
		return customerData;
	}
	
	public Customer updateCustomer(Long id, Customer customer){
		Customer updatedCustomer = null;
		Optional<Customer> customerData = repository.findById(id);
		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setName(customer.getName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			updatedCustomer = repository.save(_customer);
		}
		return updatedCustomer;
	}
	
	

}
