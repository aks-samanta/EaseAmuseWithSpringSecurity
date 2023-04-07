package com.EaseAmuse.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.BookingDto;
import com.EaseAmuse.payloads.CustomerDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.payloads.TicketDto;
import com.EaseAmuse.services.AmusementParkServices;
import com.EaseAmuse.services.BookingServices;
import com.EaseAmuse.services.CustomerServices;
import com.EaseAmuse.services.TicketServices;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	private CustomerServices customerServices;

	@Autowired
	private BookingServices bookingServices;

	@Autowired
	private TicketServices ticketServices;

	@Autowired
	private AmusementParkServices parkServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/")
	public ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody CustomerDto customerDTO) {

		customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

		return new ResponseEntity<CustomerDto>(this.customerServices.registerCustomer(customerDTO), HttpStatus.CREATED);

	}

	@GetMapping("/signIn")
	public ResponseEntity<CustomerDto> getLoggedInCustomerDetailsHandler(Authentication auth) {

		CustomerDto customer = customerServices.findByEmail(auth.getName());

		// to get the token in body, pass HttpServletResponse inside this method
		// parameter
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));
		System.out.println("signIn");
		return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);

	}

	@GetMapping("/")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<CustomerDto> getLoggedInCustomer() {
		System.out.println("/");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<CustomerDto>(this.customerServices.getCustomerById(loggedInUserId), HttpStatus.FOUND);

	}

	@PutMapping("/")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDTO) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<CustomerDto>(this.customerServices.updateCustomer(loggedInUserId, customerDTO),
				HttpStatus.OK);
	}

	@DeleteMapping("/")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	public ResponseEntity<CustomerDto> deleteCustomer() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<CustomerDto>(this.customerServices.deleteCustomer(loggedInUserId), HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Integer customerId) {

		return new ResponseEntity<CustomerDto>(this.customerServices.getCustomerById(customerId), HttpStatus.FOUND);

	}

	@GetMapping("/amusementParks/{city}")
	@PreAuthorize("hasAnyRole('CUSTOMER','MANAGER', 'ADMIN')")
	public ResponseEntity<List<AmusementParkDto>> getAmusementParksByCity(@PathVariable("city") String city) {

		return new ResponseEntity<List<AmusementParkDto>>(this.parkServices.getAmusementParksByCity(city),
				HttpStatus.FOUND);

	}

	@GetMapping("/dailyActivities/{parkId}")
	@PreAuthorize("hasAnyRole('CUSTOMER','MANAGER', 'ADMIN')")
	public ResponseEntity<List<DailyActivityDto>> getDailyActivityOfPark(@PathVariable("parkId") Integer parkId) {

		return new ResponseEntity<List<DailyActivityDto>>(this.customerServices.getDailyActivityOfPark(parkId),
				HttpStatus.FOUND);

	}

	@GetMapping("/bookings/")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BookingDto> getBooking() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<BookingDto>(this.bookingServices.createBooking(loggedInUserId), HttpStatus.OK);

	}

	@GetMapping("/allBookings")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<List<BookingDto>> getAllBookingsOfCustomer() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<List<BookingDto>>(this.bookingServices.getAllBookingsOfCustomer(loggedInUserId),
				HttpStatus.OK);

	}

	@PutMapping("/bookings/cancelBookings")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BookingDto> cancelBooking(Integer bookingId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<BookingDto>(this.bookingServices.cancelBooking(bookingId, loggedInUserId),
				HttpStatus.OK);

	}

	@PutMapping("/tickets/cancelTickets")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<TicketDto> cancelTicket(Integer ticketId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<TicketDto>(this.ticketServices.cancelTicket(loggedInUserId, ticketId), HttpStatus.OK);
	}

	@GetMapping("/bookings/{bookingId}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Integer bookingId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<BookingDto>(this.bookingServices.getBookingById(loggedInUserId, bookingId),
				HttpStatus.OK);
	}

	@PostMapping("/tickets")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<TicketDto> bookTicket(@Valid @RequestBody TicketDto ticketDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.customerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<TicketDto>(this.ticketServices.createTicket(loggedInUserId, ticketDto),
				HttpStatus.CREATED);

	}

}
