package com.customers.springrest.h2db.springbootrestinmemoryh2db.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.model.Customer;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.repo.CustomerRepository;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.service.CustomerService;

@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
public class CustomerServiceUnitTest extends AbstractJUnit4SpringContextTests {

	@InjectMocks
	CustomerService customerService;
	
	@Mock
	CustomerRepository mockCustomerRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAllCustomersTest()
	{
		List<Customer> list = new ArrayList<Customer>();
		Customer custOne = new Customer("John", 42);
		Customer custTwo = new Customer("Petra", 34);
		Customer custThree = new Customer("Darcy", 23);
		
		list.add(custOne);
		list.add(custTwo);
		list.add(custThree);
		
		when(mockCustomerRepository.findAll()).thenReturn(list);
		
		//test
		List<Customer> custList = customerService.findAllCustomers();
		assertEquals(3, custList.size());
		verify(mockCustomerRepository, times(1)).findAll();		
	}
	
	@Test
	public void findByIdTest(){
		final Customer customer = new Customer("John", 42);
		given(mockCustomerRepository.findById(1L)).willReturn(Optional.of(customer));
		final Optional<Customer> expected = customerService.findById(1L);
		assertThat(expected).isNotNull();
		
	}
	
	@Test
	public void findByAgeTest(){
		List<Customer> list = new ArrayList<Customer>();
		Customer custOne = new Customer("Sam", 30);
		Customer custTwo = new Customer("Dominic", 30);
		Customer custThree = new Customer("Darcy", 30);
		
		list.add(custOne);
		list.add(custTwo);
		list.add(custThree);
		
		when(mockCustomerRepository.findByAge(30)).thenReturn(list);
		
		//test
		List<Customer> custList = customerService.findByAge(30);
		assertEquals(3, custList.size());
	}
	
	@Test
	public void deleteCustomerTest(){
		final Long customerId = 2L;
		customerService.deleteById(customerId);
		verify(mockCustomerRepository, times(1)).deleteById(customerId);
	}
	
	@Test
	public void deleteAllCustomersTest(){
		List<Customer> list = new ArrayList<Customer>();
		Customer custOne = new Customer("John", 42);
		Customer custTwo = new Customer("Petra", 34);
		Customer custThree = new Customer("Darcy", 23);
		
		list.add(custOne);
		list.add(custTwo);
		list.add(custThree);
		
		when(mockCustomerRepository.findAll()).thenReturn(list);
		
		// test
		customerService.deleteAllCustomers();
		verify(mockCustomerRepository, times(1)).deleteAll();
	}
	
	
	@Test
	public void updateCustomerTest(){
        Customer customer = new Customer("Kilroy", 32);
        when(mockCustomerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(mockCustomerRepository.save(customer)).thenReturn(customer);
		//test
		customerService.updateCustomer(1L, customer);
		verify(mockCustomerRepository, times(1)).findById(1L);
		verify(mockCustomerRepository, times(1)).save(customer);
	}
	
	@Test
	public void findAllCustomersOrderedByAgeInDescentingOrderTest()
	{
		List<Customer> sortedList = new ArrayList<Customer>();
		Customer custOne = new Customer("John", 42);
		Customer custTwo = new Customer("Petra", 34);
		Customer custThree = new Customer("Darcy", 23);
		
		sortedList.add(custOne);
		sortedList.add(custTwo);
		sortedList.add(custThree);
		
		when(mockCustomerRepository.findAllByOrderByAgeDesc()).thenReturn(sortedList);
		
		//test
		List<Customer> custList = customerService.findAllCustomersSortedByAgeInDescendingOrder();
		assertEquals(3, custList.size());
		verify(mockCustomerRepository, times(1)).findAllByOrderByAgeDesc();		
	}
}
