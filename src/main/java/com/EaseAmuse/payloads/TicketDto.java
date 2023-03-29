package com.EaseAmuse.payloads;

import com.EaseAmuse.models.TicketStatus;
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
public class TicketDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer ticketId;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Double amount;
	@JsonProperty(access = Access.READ_WRITE)
	private Integer dailyActivitiesId;
	
	@JsonProperty(access = Access.READ_WRITE)
	private Integer noOfPerson;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String dailyActivityName;
	
	@JsonProperty(access = Access.READ_ONLY)
	private TicketStatus ticketStatus;
}
