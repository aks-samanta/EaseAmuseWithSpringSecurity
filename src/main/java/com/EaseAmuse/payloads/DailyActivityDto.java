package com.EaseAmuse.payloads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyActivityDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer dailyActivityId;

	@JsonProperty(access = Access.READ_WRITE)
	private Integer slotsRemaining;

	@JsonProperty(access = Access.READ_WRITE)
	private Date activityDate;

	@JsonProperty(access = Access.READ_ONLY)
	private String activityName;

	@JsonProperty(access = Access.READ_ONLY)
	private Double activityCharges;

	@JsonProperty(access = Access.READ_ONLY)
	private String amusementParkName;

	@JsonProperty(access = Access.READ_ONLY)
	private String amusementParkCity;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer activityId;
}
