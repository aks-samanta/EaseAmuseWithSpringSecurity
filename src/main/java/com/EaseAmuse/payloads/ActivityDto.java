package com.EaseAmuse.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer activityId;

	@JsonProperty(access = Access.READ_WRITE)
	private String name;

	@JsonProperty(access = Access.READ_WRITE)
	private String description;

	@JsonProperty(access = Access.READ_WRITE)
	private Double charges;

	@JsonProperty(access = Access.READ_ONLY)
	private Integer amusementParkId;

	@JsonProperty(access = Access.READ_ONLY)
	private String amusementParkName;

	@JsonProperty(access = Access.READ_ONLY)
	private String amusementParkCity;

}
