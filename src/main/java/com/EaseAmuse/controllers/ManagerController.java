package com.EaseAmuse.controllers;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EaseAmuse.config.SecurityConstants;
import com.EaseAmuse.payloads.ActivityDto;

import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.CustomerDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.payloads.ManagerDto;
import com.EaseAmuse.services.ManagerServices;

@RestController
@RequestMapping("/api/managers")
@CrossOrigin(origins = "*")
public class ManagerController {

	@Autowired
	private ManagerServices managerServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/")
	public ResponseEntity<ManagerDto> createManager(@Valid @RequestBody ManagerDto managerDto) {

		managerDto.setPassword(passwordEncoder.encode(managerDto.getPassword()));

		return new ResponseEntity<>(this.managerServices.insertManager(managerDto), HttpStatus.CREATED);

	}

	@GetMapping("/signIn")
	public ResponseEntity<ManagerDto> getLoggedInManagerDetailsHandler(Authentication auth, HttpServletResponse response) {

		ManagerDto manager = managerServices.getManagerByEmail(auth.getName());
		// to get the token in body, pass HttpServletResponse inside this method
				// parameter
			System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));

			return new ResponseEntity<>(manager, HttpStatus.ACCEPTED);
	}
	@GetMapping("/")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<ManagerDto> getLoggedInManager() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return new ResponseEntity<ManagerDto>(this.managerServices.getManagerByEmail(auth.getPrincipal().toString()), HttpStatus.FOUND);

	}
	@PutMapping("/")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<ManagerDto> updateManager(@Valid @RequestBody ManagerDto managerDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.managerServices.updateManager(loggedInUserId, managerDto), HttpStatus.OK);
	}

	@DeleteMapping("/")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public ResponseEntity<ManagerDto> deleteManager() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.managerServices.deleteManager(loggedInUserId), HttpStatus.OK);

	}

	@PostMapping("/amusementParks")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<AmusementParkDto> createAmusementPark(@Valid @RequestBody AmusementParkDto parkDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.managerServices.createAmusementPark(parkDto, loggedInUserId), HttpStatus.CREATED);

	}

	@PostMapping("/dailyActivities")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<DailyActivityDto> createDailyActivity(@Valid @RequestBody DailyActivityDto dailyActivityDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.managerServices.createDailyActivity(loggedInUserId, dailyActivityDto),
				HttpStatus.OK);

	}

	@GetMapping("/dailyActivities")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<DailyActivityDto>> getAllDailyActivities() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<>(this.managerServices.getAllDailyActivities(loggedInUserId), HttpStatus.OK);
	}

	@GetMapping("/activities")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<ActivityDto>> getAllActivities() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<List<ActivityDto>>(this.managerServices.getAllActivities(loggedInUserId),
				HttpStatus.FOUND);

	}

	@PostMapping("/activities")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<ActivityDto> addActivity(@Valid @RequestBody ActivityDto activityDto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<ActivityDto>(this.managerServices.createActivity(loggedInUserId, activityDto),
				HttpStatus.CREATED);

	}

	@GetMapping("/amusementParks")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<AmusementParkDto> getAmusementPark() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer loggedInUserId = this.managerServices.getUserIdByEmail(auth.getPrincipal().toString());

		return new ResponseEntity<AmusementParkDto>(this.managerServices.getAmusementPark(loggedInUserId),
				HttpStatus.FOUND);

	}

}
