package com.EaseAmuse.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DailyActivities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dailyActivityId;
	private Integer slotsRemaining;
	private Date activityDate;
	private String name;

	@ManyToOne
	@JoinColumn(name = "activityId")
	private Activity activity;

	@ManyToOne
	@JoinColumn(name = "parkId")
	private AmusementPark amusementPark;

	@OneToMany(mappedBy = "dailyActivity", cascade = CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<>();

}
