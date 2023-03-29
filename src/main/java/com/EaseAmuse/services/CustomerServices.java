package com.EaseAmuse.services;

import java.util.List;

import com.EaseAmuse.exceptions.CustomerException;
import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.payloads.CustomerDto;
import com.EaseAmuse.payloads.DailyActivityDto;

public interface CustomerServices {

	CustomerDto registerCustomer(CustomerDto customerDTO);

	CustomerDto getCustomerById(Integer customerId) throws CustomerException;

	CustomerDto updateCustomer(Integer customerId, CustomerDto customerDTO) throws CustomerException;

	CustomerDto deleteCustomer(Integer customerId) throws CustomerException;

	List<CustomerDto> getCustomersDetails() throws CustomerException;

	List<DailyActivityDto> getDailyActivityOfPark(Integer parkId) throws ResourceNotFoundException;

	Integer getUserIdByEmail(String email);

	CustomerDto findByEmail(String name);
}
