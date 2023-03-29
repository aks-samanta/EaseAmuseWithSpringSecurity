package com.EaseAmuse.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer customerId;

	@NotEmpty
	@Size(min = 3, max = 30, message = "Customer name length should be 3 to 30 characters long")
	private String customerName;

	@Email
	private String email;

	@NotEmpty
	@Size(min = 10, max = 10, message = "Customer mobile length should be 10 characters long")
	private String mobile;

	@NotEmpty
	@Size(min = 8, max = 15, message = "Customer password length should be 8 to 15 characters long")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

}
