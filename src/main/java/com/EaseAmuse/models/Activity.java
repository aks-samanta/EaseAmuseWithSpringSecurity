package com.EaseAmuse.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer activityId;
	private String name;
	private String description;
	private Double charges;

	@ManyToOne
	@JoinColumn(name = "parkId")
	private AmusementPark amusementPark;

	@OneToMany(mappedBy = "activity", cascade = CascadeType.MERGE)
	private List<DailyActivity> dailyActivities = new ArrayList<>();
}
