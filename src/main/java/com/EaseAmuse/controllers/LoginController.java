package com.EaseAmuse.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EaseAmuse.exceptions.LoginException;
import com.EaseAmuse.models.CurrentUserSession;
import com.EaseAmuse.models.UserType;
import com.EaseAmuse.payloads.CustomerOutputDto;
import com.EaseAmuse.payloads.LoginDto;
import com.EaseAmuse.services.LoginService;
import com.EaseAmuse.models.Customer;
import com.EaseAmuse.repositories.CustomerRepo;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private CustomerRepo customerRepository;
	
	@GetMapping("/signIn")
	public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(Authentication auth){
		
		
		Customer customer= customerRepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
		
		 //to get the token in body, pass HttpServletResponse inside this method parameter 
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));
		 
		 
		 return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
		
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logOutHandler(@RequestParam(name = "userId") Integer userId,
			@RequestParam(name = "userType") UserType userType) throws LoginException {

		String msg = loginService.logOut(userId, userType);

		return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);

	}

}
