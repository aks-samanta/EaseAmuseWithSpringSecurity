package com.EaseAmuse.services;

import java.util.List;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.payloads.ActivityDto;

public interface ActivityServices {

	// create
	ActivityDto createActivity(Integer managerId, ActivityDto activityDto);

	// readAll
	List<ActivityDto> getAllActivities();

	// readAllByCharges
	List<ActivityDto> getActivitiesByCharges(Double charges) throws ResourceNotFoundException;

	// readAllByParkId
	List<ActivityDto> getAllActivitiesOfPark(Integer parkId) throws ResourceNotFoundException;

	// readById
	ActivityDto getActivityById(Integer Id) throws ResourceNotFoundException;

	// update
	ActivityDto updateActivity(Integer managerId, Integer activityId, ActivityDto activityDto)
			throws ResourceNotFoundException;

	// delete
	ActivityDto deleteActivity(Integer managerId, Integer activityId) throws ResourceNotFoundException;
}
