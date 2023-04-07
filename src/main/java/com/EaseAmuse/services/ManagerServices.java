package com.EaseAmuse.services;

import java.util.Date;
import java.util.List;

import com.EaseAmuse.exceptions.ResourceNotFoundException;

import com.EaseAmuse.payloads.ActivityDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.payloads.ManagerDto;

public interface ManagerServices {

	public ManagerDto insertManager(ManagerDto managerInpDto) throws ResourceNotFoundException;

	public ManagerDto updateManager(Integer managerId, ManagerDto managerInpDto) throws ResourceNotFoundException;

	public ManagerDto deleteManager(Integer managerId) throws ResourceNotFoundException;

	public List<DailyActivityDto> getAllDailyActivities(Integer managerId) throws ResourceNotFoundException;

	public List<DailyActivityDto> getDailyActivitiesCustomerwise(Integer customerId) throws ResourceNotFoundException;

	public List<DailyActivityDto> getDailyActivitiesDatewise(Integer managerId, Date activityDate)
			throws ResourceNotFoundException;

	public List<ActivityDto> getAllActivities(Integer managerId) throws ResourceNotFoundException;

	public ActivityDto createActivity(Integer managerId, ActivityDto activityDto);

	public AmusementParkDto getAmusementPark(Integer managerId);

	public DailyActivityDto createDailyActivity(Integer managerId, DailyActivityDto dailyActivityDto)
			throws ResourceNotFoundException;

	public Integer getUserIdByEmail(String email);

	public ManagerDto getManagerByEmail(String email);

	public AmusementParkDto createAmusementPark(AmusementParkDto amusementParkInpDto, Integer managerId)
			throws ResourceNotFoundException;
}
