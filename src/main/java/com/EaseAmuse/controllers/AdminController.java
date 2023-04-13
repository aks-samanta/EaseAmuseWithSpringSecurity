package com.EaseAmuse.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EaseAmuse.payloads.AdminDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.CustomerDto;
import com.EaseAmuse.payloads.ManagerDto;
import com.EaseAmuse.services.AdminServices;
import com.EaseAmuse.services.ManagerServices;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminServices adminServices;

	@Autowired
	private ManagerServices managerServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/")
	public ResponseEntity<AdminDto> createAdmin(@Valid @RequestBody AdminDto adminDto) {

		adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));

		return new ResponseEntity<>(this.adminServices.insertAdmin(adminDto), HttpStatus.CREATED);

	}

	@GetMapping("/signIn")
	public ResponseEntity<AdminDto> getLoggedInAdminDetailsHandler(Authentication auth) {

		AdminDto admin = adminServices.getAdminByEmail(auth.getName());

		// to get the token in body, pass HttpServletResponse inside this method
		// parameter
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));

		return new ResponseEntity<>(admin, HttpStatus.ACCEPTED);

	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> getLoggedInCustomer() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return new ResponseEntity<AdminDto>(this.adminServices.getAdminByEmail(auth.getPrincipal().toString()),
				HttpStatus.FOUND);

	}

	@PutMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> updateAdmin(@Valid @RequestBody AdminDto adminDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.adminServices.getAdminIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.adminServices.updateAdmin(loggedInUserId, adminDto), HttpStatus.OK);

	}

	@PostMapping("/managers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ManagerDto> createManager(@Valid @RequestBody ManagerDto managerDto) {

		return new ResponseEntity<>(this.managerServices.insertManager(managerDto), HttpStatus.CREATED);

	}

	@PostMapping("/amusementParks")
	@PreAuthorize("hasRole('MANAGER', 'ADMIN')")
	public ResponseEntity<AmusementParkDto> createAmusementPark(@Valid @RequestBody AmusementParkDto parkDto) {

		return new ResponseEntity<>(this.adminServices.createAmusementPark(parkDto), HttpStatus.CREATED);

	}

}
