package com.EaseAmuse.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Manager {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer managerId;
	private String name;
	private String password;
	private String email;
	private String mobile;
	private String role;
	@OneToOne
	private AmusementPark amusementPark;

	
}
