package com.EaseAmuse.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EaseAmuse.payloads.AdminDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.ManagerDto;
import com.EaseAmuse.services.AdminServices;
import com.EaseAmuse.services.ManagerServices;

@RestController
@RequestMapping("/api/admins")
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
	public ResponseEntity<AdminDto> getLoggedInCustomerDetailsHandler(Authentication auth) {

		AdminDto admin = adminServices.getAdminByEmail(auth.getName());

		// to get the token in body, pass HttpServletResponse inside this method
		// parameter
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));

		return new ResponseEntity<>(admin, HttpStatus.ACCEPTED);

	}

	@PutMapping("/")
	public ResponseEntity<AdminDto> updateAdmin(@Valid @RequestBody AdminDto adminDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.adminServices.getAdminIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.adminServices.updateAdmin(loggedInUserId, adminDto), HttpStatus.OK);

	}

	@PostMapping("/managers")
	public ResponseEntity<ManagerDto> createManager(@Valid @RequestBody ManagerDto managerDto) {

		return new ResponseEntity<>(this.managerServices.insertManager(managerDto), HttpStatus.CREATED);

	}

	@PostMapping("/amusementParks")
	public ResponseEntity<AmusementParkDto> createAmusementPark(@Valid @RequestBody AmusementParkDto parkDto) {

		return new ResponseEntity<>(this.adminServices.createAmusementPark(parkDto), HttpStatus.CREATED);

	}

}
