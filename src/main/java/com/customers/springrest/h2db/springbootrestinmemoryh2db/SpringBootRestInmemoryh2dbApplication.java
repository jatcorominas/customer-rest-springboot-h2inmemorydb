package com.customers.springrest.h2db.springbootrestinmemoryh2db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.service.CustomerService;

@SpringBootApplication
public class SpringBootRestInmemoryh2dbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestInmemoryh2dbApplication.class, args);
	}
	
	@Bean("customerService")
	public CustomerService getCustomerRepositoryService(){
		return new CustomerService();
	}
}
