package com.customers.springrest.h2db.springbootrestinmemoryh2db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByAge(int age);
	List<Customer> findAllByOrderByAgeDesc();
}
