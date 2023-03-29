package com.EaseAmuse.services;

import java.util.Date;
import java.util.List;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.payloads.ActivityDto;
import com.EaseAmuse.payloads.AdminDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.DailyActivityDto;

public interface AdminServices {

	public AdminDto insertAdmin(AdminDto adminInpDto) throws ResourceNotFoundException;

	public AdminDto updateAdmin(Integer adminId, AdminDto adminInpDto) throws ResourceNotFoundException;

	public AdminDto deleteAdmin(Integer adminId) throws ResourceNotFoundException;

	public AmusementParkDto createAmusementPark(AmusementParkDto amusementParkInpDto) throws ResourceNotFoundException;

	public List<DailyActivityDto> getAllDailyActivities(Integer adminId) throws ResourceNotFoundException;

	public List<DailyActivityDto> getDailyActivitiesCustomerwise(Integer customerId) throws ResourceNotFoundException;

	public List<DailyActivityDto> getDailyActivitiesDatewise(Integer adminId, Date activityDate)
			throws ResourceNotFoundException;

	public List<ActivityDto> getAllActivities(Integer adminId) throws ResourceNotFoundException;

	public ActivityDto createActivity(Integer adminId, ActivityDto activityDto);

	public AmusementParkDto getAmusementPark(Integer adminId);

	public Integer getAdminIdByEmail(String email);

	AdminDto getAdminByEmail(String email);

}
