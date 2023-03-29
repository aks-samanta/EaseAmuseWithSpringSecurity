package com.EaseAmuse.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.exceptions.UnauthorisedException;
import com.EaseAmuse.models.Activity;
import com.EaseAmuse.models.AmusementPark;
import com.EaseAmuse.models.Manager;
import com.EaseAmuse.payloads.ActivityDto;
import com.EaseAmuse.repositories.ActivityRepo;
import com.EaseAmuse.repositories.AmusementParkRepo;
import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class ActivityServicesImpl implements ActivityServices {

	@Autowired
	private ManagerRepo managerRepo;

	@Autowired
	private ActivityRepo activityRepo;

	@Autowired
	private AmusementParkRepo parkRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ActivityDto createActivity(Integer managerId, ActivityDto activityDto) {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "ManagerId", managerId.toString()));

		AmusementPark park = manager.getAmusementPark();

		Activity activity = this.modelMapper.map(activityDto, Activity.class);

		activity.setAmusementPark(park);
		park.getActivities().add(activity);

		AmusementPark updatedPark = this.parkRepo.save(park);

		Activity createdActivity = updatedPark.getActivities().get(updatedPark.getActivities().size() - 1);

		return this.modelMapper.map(createdActivity, ActivityDto.class);

	}

	@Override
	public List<ActivityDto> getAllActivities() {
		List<Activity> activities = this.activityRepo.findAll();

		List<ActivityDto> activityDos = activities.stream().map((a) -> this.modelMapper.map(a, ActivityDto.class))
				.collect(Collectors.toList());

		return activityDos;
	}

	@Override
	public List<ActivityDto> getActivitiesByCharges(Double charges) throws ResourceNotFoundException {

		List<Activity> activities = this.activityRepo.findByCharges(charges);

		List<ActivityDto> activityDos = activities.stream().map((a) -> this.modelMapper.map(a, ActivityDto.class))
				.collect(Collectors.toList());

		return activityDos;
	}

	@Override
	public List<ActivityDto> getAllActivitiesOfPark(Integer parkId) throws ResourceNotFoundException {

		AmusementPark park = this.parkRepo.findById(parkId)
				.orElseThrow(() -> new ResourceNotFoundException("AmusementPark", "park Id", parkId.toString()));

		List<Activity> activities = this.activityRepo.findByAmusementPark(park);

		List<ActivityDto> activityDos = activities.stream().map((a) -> this.modelMapper.map(a, ActivityDto.class))
				.collect(Collectors.toList());

		return activityDos;
	}

	@Override
	public ActivityDto getActivityById(Integer Id) throws ResourceNotFoundException {

		Activity activity = this.activityRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Activity", "Activity Id", Id.toString()));

		return this.modelMapper.map(activity, ActivityDto.class);

	}

	@Override
	public ActivityDto updateActivity(Integer managerId, Integer activityId, ActivityDto activityDto)
			throws ResourceNotFoundException {

		Activity activity = this.activityRepo.findById(activityId)
				.orElseThrow(() -> new ResourceNotFoundException("Activity", "activity Id", activityId.toString()));

		if (activity.getAmusementPark().getManager().getManagerId() == managerId) {
			activity.setCharges(activityDto.getCharges());
			activity.setName(activityDto.getName());
			activity.setDescription(activityDto.getDescription());

			Activity savedActivity = this.activityRepo.save(activity);

			return this.modelMapper.map(savedActivity, ActivityDto.class);
		} else {
			throw new UnauthorisedException("You are not authorised to update this Activity!");
		}
	}

	@Override
	public ActivityDto deleteActivity(Integer managerId, Integer activityId) throws ResourceNotFoundException {

		Activity activity = this.activityRepo.findById(activityId)
				.orElseThrow(() -> new ResourceNotFoundException("Activity", "activity Id", activityId.toString()));

		if (activity.getAmusementPark().getManager().getManagerId() == managerId) {

			this.activityRepo.delete(activity);

			return this.modelMapper.map(activity, ActivityDto.class);
		} else {
			throw new UnauthorisedException("You are not authorised to delete this Activity!");
		}
	}

}
