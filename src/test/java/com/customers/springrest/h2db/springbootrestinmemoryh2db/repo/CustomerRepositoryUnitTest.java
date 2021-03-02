package com.customers.springrest.h2db.springbootrestinmemoryh2db.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import com.customers.springrest.h2db.springbootrestinmemoryh2db.model.Customer;
import com.customers.springrest.h2db.springbootrestinmemoryh2db.repo.CustomerRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryUnitTest {

		@Autowired
		private TestEntityManager entityManager;
		
		@Autowired
		CustomerRepository customerRepository;
		
		@Test
		public void should_find_customers_if_repository_is_not_empty() {
			Iterable<Customer> customers = customerRepository.findAll();
			
			assertThat(customers).isNotEmpty();
		}
		
		
		@Test
		public void should_find_customer_by_id(){
			Customer customer1 = new Customer("Jake", 32);
			entityManager.persist(customer1);
			
			Customer customer2 = new Customer("Muriel", 28);
			entityManager.persist(customer2);
			
			Customer foundCustomer = customerRepository.findById(customer2.getId()).get();
			
			assertThat(foundCustomer).isEqualTo(customer2);
		}
		
		@Test
		public void should_find_by_age_equal_to_25(){
			Customer customer1 = new Customer("Jason", 33);
			entityManager.persist(customer1);
			
			Customer customer2 = new Customer("Muriel", 25);
			entityManager.persist(customer2);
			
			Customer customer3 = new Customer("Sandra", 25);
			entityManager.persist(customer3);
			
			Iterable<Customer> customers25YearsOfAge = customerRepository.findByAge(25);
			
			assertThat(customers25YearsOfAge).hasSize(2).contains(customer2, customer3);
			
		}
		
		@Test
		public void should_update_customer_by_id(){
			Customer customer1 = new Customer("Marvin", 35);
			entityManager.persist(customer1);
			
			Customer customer2 = new Customer("McKenzie", 57);
			entityManager.persist(customer2);
			
			Customer updatedCustomer = new Customer("McKenzie", 59);
			updatedCustomer.setActive(true);
			
			Customer customer = customerRepository.findById(customer2.getId()).get();
			customer.setName(updatedCustomer.getName());
			customer.setActive(updatedCustomer.isActive());
			customer.setAge(updatedCustomer.getAge());
			customerRepository.save(customer);
			
			Customer checkCustomer = customerRepository.findById(customer2.getId()).get();
			
			assertThat(checkCustomer.getId()).isEqualTo(customer2.getId());
			assertThat(checkCustomer.getName()).isEqualTo(customer2.getName());
			assertThat(checkCustomer.getAge()).isEqualTo(customer2.getAge());
			assertThat(checkCustomer.isActive()).isEqualTo(customer2.isActive());
		}
		
		@Test
		public void should_delete_customer_by_id(){
			Customer customer1 = new Customer("Stagus", 30);
			entityManager.persist(customer1);
			
			Customer customer2 = new Customer("Judas", 40);
			entityManager.persist(customer2);
			
			Customer customer3 = new Customer("Kilroy", 20);
			entityManager.persist(customer3);
			
			customerRepository.deleteById(customer2.getId());
			
			Iterable<Customer> customers =  customerRepository.findAll();
			assertThat(customers).doesNotContain(customer2);
			
		}
		
		@Test
		public void should_delete_all_customers(){
			entityManager.persist(new Customer("Agatha", 46));
			entityManager.persist(new Customer("Bart", 38));
			customerRepository.deleteAll();
			customerRepository.deleteAll();
			assertThat(customerRepository.findAll()).isEmpty();
			
		}
		
		@Test
		public void givenSeveralCustomersWhenSortedByAgeDescTheFirstIsTheOldest(){
			Customer customer1 = new Customer("Sam", 30);
			entityManager.persist(customer1);
			
			Customer customer2 = new Customer("Jake", 40);
			entityManager.persist(customer2);
			
			Customer customer3 = new Customer("Kathy", 20);
			entityManager.persist(customer3);
			
			List<Customer> customersSortedInDescOrder = customerRepository.findAllByOrderByAgeDesc();
			Customer actual = customersSortedInDescOrder.get(0);
			
			assertTrue(customer2.getName().equals(actual.getName()));
			assertEquals(customer2.getAge(),actual.getAge());
			
		}
			
}
