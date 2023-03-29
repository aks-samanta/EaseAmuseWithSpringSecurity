package com.EaseAmuse.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer adminId;

	@NotEmpty
	@Size(min = 3, max = 30, message = "Admin name length should be 3 to 30 characters long")
	private String name;

	@Email
	private String email;

	@NotEmpty
	@Size(min = 10, max = 10, message = "Admin mobile length should be 10 characters long")
	private String mobile;

	@NotEmpty
	@Size(min = 8, max = 15, message = "Admin password length should be 8 to 15 characters long")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

}
