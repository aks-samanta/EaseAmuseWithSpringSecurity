package com.EaseAmuse.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmusementParkDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;

	@JsonProperty(access = Access.READ_WRITE)
	private String name;

	@JsonProperty(access = Access.READ_WRITE)
	private String city;

	@JsonProperty(access = Access.READ_WRITE)
	private Integer managerId;

	@JsonProperty(access = Access.READ_ONLY)
	private String managerName;

	@JsonProperty(access = Access.READ_ONLY)
	private String managerMobile;

}
