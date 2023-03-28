package com.EaseAmuse.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmusementPark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer parkId;
	private String name;
	private String city;
	@OneToOne
	@JoinColumn(name = "managerId")
	private Manager manager;

	@OneToMany(mappedBy = "amusementPark", cascade = CascadeType.ALL)
	private List<Activity> activities = new ArrayList<>();

	@OneToMany(mappedBy = "amusementPark", cascade = CascadeType.ALL)
	private List<DailyActivity> dailyActivities = new ArrayList<>();
}
