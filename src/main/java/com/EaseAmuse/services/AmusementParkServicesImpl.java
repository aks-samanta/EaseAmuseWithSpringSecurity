package com.EaseAmuse.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.exceptions.UnauthorisedException;
import com.EaseAmuse.models.AmusementPark;
import com.EaseAmuse.models.Manager;
import com.EaseAmuse.payloads.AmusementParkDto;
import com.EaseAmuse.repositories.AmusementParkRepo;
import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class AmusementParkServicesImpl implements AmusementParkServices {

	@Autowired
	private ManagerRepo managerRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AmusementParkRepo amusementParkRepo;

	@Override
	public AmusementParkDto createAmusementPark(AmusementParkDto parkDto) {

		Manager manager = this.managerRepo.findById(parkDto.getManagerId()).orElseThrow(
				() -> new ResourceNotFoundException("Manager", "Manager Id", parkDto.getManagerId().toString()));
		AmusementPark park = this.modelMapper.map(parkDto, AmusementPark.class);

		park.setManager(manager);
		manager.setAmusementPark(park);

		this.amusementParkRepo.save(park);
		this.managerRepo.save(manager);

		return this.modelMapper.map(park, AmusementParkDto.class);

	}

	@Override
	public AmusementParkDto getAmusementParkById(Integer parkId) throws ResourceNotFoundException {

		AmusementPark park = this.amusementParkRepo.findById(parkId)
				.orElseThrow(() -> new ResourceNotFoundException("Amusement Park", "Park Id", parkId.toString()));
		return this.modelMapper.map(park, AmusementParkDto.class);
	}

	@Override
	public List<AmusementParkDto> getAmusementParksByCity(String city) throws ResourceNotFoundException {

		List<AmusementPark> parks = this.amusementParkRepo.findByCity(city)
				.orElseThrow(() -> new ResourceNotFoundException("Amusemement Park", "City", city));

		List<AmusementParkDto> parkDtos = parks.stream().map(park -> this.modelMapper.map(park, AmusementParkDto.class))
				.collect(Collectors.toList());

		return parkDtos;
	}

	@Override
	public List<AmusementParkDto> getAllAmusementParks() {

		List<AmusementPark> parks = this.amusementParkRepo.findAll();

		List<AmusementParkDto> parkDtos = parks.stream().map(park -> this.modelMapper.map(park, AmusementParkDto.class))
				.collect(Collectors.toList());

		return parkDtos;
	}

	@Override
	public AmusementParkDto updateAmusementPark(Integer managerId, Integer parkId, AmusementParkDto parkDto)
			throws ResourceNotFoundException {

		Manager manager = this.managerRepo.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "Manager Id", managerId.toString()));

		AmusementPark park = this.amusementParkRepo.findById(parkId)
				.orElseThrow(() -> new ResourceNotFoundException("Amusement Park", "Park Id", parkId.toString()));

		if (manager.getAmusementPark().getId() == park.getId()) {
			park.setCity(parkDto.getCity());
			park.setName(parkDto.getName());
			AmusementPark updatedPark = this.amusementParkRepo.save(park);
			return this.modelMapper.map(updatedPark, AmusementParkDto.class);

		} else {
			throw new UnauthorisedException("You Are Not the manager of this Park so you cannot update this Park!");
		}

	}

	@Override
	public AmusementParkDto removeAmusementpark(Integer parkId) throws ResourceNotFoundException {
		AmusementPark park = this.amusementParkRepo.findById(parkId)
				.orElseThrow(() -> new ResourceNotFoundException("Amusement Park", "Park Id", parkId.toString()));

		this.amusementParkRepo.delete(park);

		return this.modelMapper.map(park, AmusementParkDto.class);

	}

	@Override
	public AmusementParkDto createAmusementPark(AmusementParkDto parkDto, Integer managerId) {
		Manager manager = this.managerRepo.findById(managerId).orElseThrow(
				() -> new ResourceNotFoundException("Manager", "managerId", managerId.toString()));
		AmusementPark park = new AmusementPark();
		park.setCity(parkDto.getCity());
		park.setName(parkDto.getName());
System.out.println(park);
		park.setManager(manager);
		manager.setAmusementPark(park);
System.out.println(manager);
		this.amusementParkRepo.save(park);
		this.managerRepo.save(manager);

		return this.modelMapper.map(park, AmusementParkDto.class);
	}

}
