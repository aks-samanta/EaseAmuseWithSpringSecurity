package com.EaseAmuse.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.exceptions.UnauthorisedException;
import com.EaseAmuse.models.Activity;
import com.EaseAmuse.models.AmusementPark;
import com.EaseAmuse.models.Customer;
import com.EaseAmuse.models.DailyActivity;
import com.EaseAmuse.models.Manager;
import com.EaseAmuse.payloads.ActivityDto;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.payloads.DailyActivityDto;
import com.EaseAmuse.payloads.ManagerDto;
import com.EaseAmuse.repositories.ActivityRepo;
import com.EaseAmuse.repositories.AmusementParkRepo;
import com.EaseAmuse.repositories.CustomerRepo;
import com.EaseAmuse.repositories.DailyActivityRepo;
import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class ManagerServicesImpl implements ManagerServices {

	@Autowired
	ManagerRepo managerRepo;

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
	public ManagerDto insertManager(ManagerDto managerInpDto) throws ResourceNotFoundException {

		Manager manager = this.modelMapper.map(managerInpDto, Manager.class);

		Manager savedManager = this.managerRepo.save(manager);

		return this.modelMapper.map(savedManager, ManagerDto.class);

	}

	@Override
	public ManagerDto updateManager(Integer managerId, ManagerDto managerInpDto) throws ResourceNotFoundException {

		Manager foundManager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "managerId", managerId.toString()));

		foundManager.setName(managerInpDto.getName());
		foundManager.setEmail(managerInpDto.getEmail());
		foundManager.setMobile(managerInpDto.getMobile());
		foundManager.setPassword(managerInpDto.getPassword());

		Manager updatedManager = this.managerRepo.save(foundManager);

		return this.modelMapper.map(updatedManager, ManagerDto.class);
	}

	@Override
	public ManagerDto deleteManager(Integer managerId) throws ResourceNotFoundException {

		Manager foundManager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "managerId", managerId.toString()));

		this.managerRepo.delete(foundManager);
		return this.modelMapper.map(foundManager, ManagerDto.class);
	}

	@Override
	public AmusementParkDto createAmusementPark(AmusementParkDto amusementParkInpDto) throws ResourceNotFoundException {

		return amusementParkServices.createAmusementPark(amusementParkInpDto);

	}

	@Override
	public List<DailyActivityDto> getAllDailyActivities(Integer managerId) throws ResourceNotFoundException {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		List<DailyActivity> dailyActivities = dailyActivityRepo.findByAmusementPark(manager.getAmusementPark());

		return dailyActivities.stream().map((da) -> this.modelMapper.map(da, DailyActivityDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public List<DailyActivityDto> getDailyActivitiesCustomerwise(Integer customerId) throws ResourceNotFoundException {

		List<DailyActivityDto> activities = new ArrayList<>();

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

		customer.getBookings().forEach(b -> b.getTickets()
				.forEach(t -> activities.add(this.modelMapper.map(t.getDailyActivity(), DailyActivityDto.class))));

		return activities;

	}

	@Override
	public List<DailyActivityDto> getDailyActivitiesDatewise(Integer managerId, Date activityDate)
			throws ResourceNotFoundException {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		List<DailyActivity> activities = dailyActivityRepo
				.findByAmusementParkAndActivityDate(manager.getAmusementPark(), activityDate);

		return activities.stream().map((da) -> this.modelMapper.map(da, DailyActivityDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public List<ActivityDto> getAllActivities(Integer managerId) throws ResourceNotFoundException {
		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		List<Activity> activities = manager.getAmusementPark().getActivities();

		return activities.stream().map((a) -> this.modelMapper.map(a, ActivityDto.class)).collect(Collectors.toList());
	}

	@Override
	public ActivityDto createActivity(Integer managerId, ActivityDto activityDto) {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		AmusementPark park = manager.getAmusementPark();

		Activity activity = this.modelMapper.map(activityDto, Activity.class);
		activity.setAmusementPark(park);
		park.getActivities().add(activity);

		AmusementPark updatedPark = this.parkRepo.save(park);

		return this.modelMapper.map(updatedPark.getActivities().get((updatedPark.getActivities().size() - 1)),
				ActivityDto.class);
	}

	@Override
	public AmusementParkDto getAmusementPark(Integer managerId) {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		return this.modelMapper.map(manager.getAmusementPark(), AmusementParkDto.class);

	}

	@Override
	public DailyActivityDto createDailyActivity(Integer managerId, DailyActivityDto dailyActivityDto)
			throws ResourceNotFoundException {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		Activity activity = this.activityRepo.findById(dailyActivityDto.getActivityId())
				.orElseThrow(() -> new ResourceNotFoundException("Activity", "Activity Id",
						dailyActivityDto.getActivityId().toString()));

		AmusementPark park = this.parkRepo.findById(manager.getAmusementPark().getParkId())
				.orElseThrow(() -> new ResourceNotFoundException("Amusement Park", "Park Id",
						manager.getAmusementPark().getParkId().toString()));

//System.out.println(park.getParkId());

		if (activity.getAmusementPark().getParkId() == manager.getAmusementPark().getParkId()) {
			DailyActivity dailyActivity = this.modelMapper.map(dailyActivityDto, DailyActivity.class);

			dailyActivity.setActivity(activity);
			dailyActivity.setName(activity.getName());
			activity.getDailyActivities().add(dailyActivity);
			dailyActivity.setAmusementPark(park);
			park.getDailyActivities().add(dailyActivity);

//			System.out.println(this.dailyActivityRepo.save(dailyActivity).getDailyActivityId());

			AmusementPark savedPark = this.parkRepo.save(park);
			System.out.println(
					savedPark.getDailyActivities().get(savedPark.getDailyActivities().size() - 1).getDailyActivityId());
			return this.modelMapper.map(savedPark.getDailyActivities().get(savedPark.getDailyActivities().size() - 1),
					DailyActivityDto.class);

		} else {
			throw new UnauthorisedException("This Activity does not belong to your Amusement Park!");
		}

	}

	@Override
	public Integer getUserIdByEmail(String email) {
		return this.managerRepo.findByEmail(email).get().getManagerId();

	}

	@Override
	public ManagerDto getManagerByEmail(String email) {
		return this.modelMapper.map(managerRepo.findByEmail(email).get(), ManagerDto.class);

	}

}
