package com.EaseAmuse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EaseAmuse.models.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	public Optional<Customer> findByEmail(String email);

	
}
