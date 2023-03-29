package com.EaseAmuse.services;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.models.Admin;
import com.EaseAmuse.payloads.ActivityDto;
import com.EaseAmuse.payloads.AdminDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.repositories.ActivityRepo;
import com.EaseAmuse.repositories.AdminRepo;
import com.EaseAmuse.repositories.AmusementParkRepo;
import com.EaseAmuse.repositories.CustomerRepo;
import com.EaseAmuse.repositories.DailyActivityRepo;

@Service
public class AdminServicesImpl implements AdminServices {

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	DailyActivityRepo dailyActivityRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AmusementParkServices amusementParkServices;

	@Autowired
	AmusementParkRepo parkRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ActivityRepo activityRepo;

	@Override
	public AdminDto insertAdmin(AdminDto adminInpDto) throws ResourceNotFoundException {

		Admin admin = this.modelMapper.map(adminInpDto, Admin.class);

		Admin savedAdmin = this.adminRepo.save(admin);

		return this.modelMapper.map(savedAdmin, AdminDto.class);

	}

	@Override
	public AdminDto updateAdmin(Integer adminId, AdminDto adminInpDto) throws ResourceNotFoundException {

		Admin foundAdmin = this.adminRepo.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("Admin", "adminId", adminId.toString()));

		foundAdmin.setName(adminInpDto.getName());
		foundAdmin.setEmail(adminInpDto.getEmail());
		foundAdmin.setMobile(adminInpDto.getMobile());
		foundAdmin.setPassword(adminInpDto.getPassword());

		Admin updatedAdmin = this.adminRepo.save(foundAdmin);

		return this.modelMapper.map(updatedAdmin, AdminDto.class);

	}

	@Override
	public AdminDto deleteAdmin(Integer adminId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmusementParkDto createAmusementPark(AmusementParkDto amusementParkInpDto) throws ResourceNotFoundException {

		return amusementParkServices.createAmusementPark(amusementParkInpDto);

	}

	@Override
	public List<DailyActivityDto> getAllDailyActivities(Integer adminId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DailyActivityDto> getDailyActivitiesCustomerwise(Integer customerId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DailyActivityDto> getDailyActivitiesDatewise(Integer adminId, Date activityDate)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityDto> getAllActivities(Integer adminId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityDto createActivity(Integer adminId, ActivityDto activityDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmusementParkDto getAmusementPark(Integer adminId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAdminIdByEmail(String email) {

		return adminRepo.findByEmail(email).get().getAdminId();

	}

	@Override
	public AdminDto getAdminByEmail(String email) {

		return this.modelMapper.map(adminRepo.findByEmail(email).get(), AdminDto.class);

	}

}
