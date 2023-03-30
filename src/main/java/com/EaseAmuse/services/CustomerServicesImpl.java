package com.EaseAmuse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.CustomerException;
import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.exceptions.UnauthorisedException;
import com.EaseAmuse.models.Admin;
import com.EaseAmuse.models.AmusementPark;
import com.EaseAmuse.models.Customer;
import com.EaseAmuse.models.Manager;
import com.EaseAmuse.payloads.CustomerDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.repositories.AdminRepo;
import com.EaseAmuse.repositories.AmusementParkRepo;
import com.EaseAmuse.repositories.CustomerRepo;
import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class CustomerServicesImpl implements CustomerServices {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AmusementParkRepo amusementParkRepo;

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private ManagerRepo managerRepo;
	
	@Override
	public CustomerDto registerCustomer(CustomerDto customerDTO) {
		
		Optional<Admin> adm = adminRepo.findByEmail(customerDTO.getEmail());
		Optional<Customer> cust = customerRepo.findByEmail(customerDTO.getEmail());
		Optional<Manager> man = managerRepo.findByEmail(customerDTO.getEmail());

		if (adm.isPresent() || cust.isPresent() || man.isPresent()) {
			throw new UnauthorisedException(
					"user already exists as Admin or Manager or Customer with Email Id : " + customerDTO.getEmail());
		}
		
		Customer customer = this.modelMapper.map(customerDTO, Customer.class);

		Customer savedCustomer = this.customerRepo.save(customer);

		return this.modelMapper.map(savedCustomer, CustomerDto.class);
	}

	@Override
	public CustomerDto getCustomerById(Integer customerId) {

		Customer foundCustomer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerException("cutomer Not ound"));

		return this.modelMapper.map(foundCustomer, CustomerDto.class);

	}

	@Override
	public CustomerDto updateCustomer(Integer customerId, CustomerDto customerDTO) throws CustomerException {
		// TODO Auto-generated method stub
		Customer foundCustomer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerException("customer not found"));

		foundCustomer.setName(customerDTO.getCustomerName());
		foundCustomer.setEmail(customerDTO.getEmail());
		foundCustomer.setMobile(customerDTO.getMobile());
		foundCustomer.setPassword(customerDTO.getPassword());

		Customer updatedCustomer = this.customerRepo.save(foundCustomer);

		return this.modelMapper.map(updatedCustomer, CustomerDto.class);

	}

	@Override
	public CustomerDto deleteCustomer(Integer customerId) throws CustomerException {
		// TODO Auto-generated method stub
		Customer foundCustomer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerException("customer not found"));

		this.customerRepo.delete(foundCustomer);
		return this.modelMapper.map(foundCustomer, CustomerDto.class);
	}

	@Override
	public List<CustomerDto> getCustomersDetails() throws CustomerException {
		// TODO Auto-generated method stub
		List<Customer> lc = this.customerRepo.findAll();

		if (lc.isEmpty()) {
			throw new CustomerException("no customer available.");
		}

		List<CustomerDto> listOfDtos = new ArrayList<>();
		for (Customer customer : lc) {
			listOfDtos.add(new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(),
					customer.getMobile(), customer.getPassword()));
		}

		return listOfDtos;
		// return this.modelMapper.map(listOfDtos, CustomerDTO.class);
	}

	@Override
	public List<DailyActivityDto> getDailyActivityOfPark(Integer parkId) {
		AmusementPark park = this.amusementParkRepo.findById(parkId)
				.orElseThrow(() -> new ResourceNotFoundException("Park", "ParkID", parkId.toString()));

		return park.getDailyActivities().stream().map((da) -> this.modelMapper.map(da, DailyActivityDto.class))
				.collect((Collectors.toList()));

	}

	@Override
	public Integer getUserIdByEmail(String email) {
		System.out.println(email);
		return this.customerRepo.findByEmail(email).get().getCustomerId();
	}

	@Override
	public CustomerDto findByEmail(String email) {

		Customer foundCustomer = this.customerRepo.findByEmail(email)
				.orElseThrow(() -> new BadCredentialsException("Incorrect Username Or Password"));

		return this.modelMapper.map(foundCustomer, CustomerDto.class);
	}

}
