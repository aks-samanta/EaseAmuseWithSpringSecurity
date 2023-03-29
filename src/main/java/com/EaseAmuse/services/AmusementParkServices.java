package com.EaseAmuse.services;

import java.util.List;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.payloads.AmusementParkDto;

public interface AmusementParkServices {

	// create
	AmusementParkDto createAmusementPark(AmusementParkDto ParkDto);

	// readById
	AmusementParkDto getAmusementParkById(Integer parkId) throws ResourceNotFoundException;

	// readByCity
	List<AmusementParkDto> getAmusementParksByCity(String city) throws ResourceNotFoundException;

	// readAll
	List<AmusementParkDto> getAllAmusementParks();

	// update
	AmusementParkDto updateAmusementPark(Integer managerId, Integer parkId, AmusementParkDto parkDto)
			throws ResourceNotFoundException;

	// delete
	AmusementParkDto removeAmusementpark(Integer parkId) throws ResourceNotFoundException;
}
