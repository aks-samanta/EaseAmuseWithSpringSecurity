package com.EaseAmuse.services;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.payloads.TicketDto;

public interface TicketServices {

	TicketDto createTicket(Integer customerId, TicketDto ticketDto) throws ResourceNotFoundException;

	TicketDto cancelTicket(Integer customerId, Integer ticketId) throws ResourceNotFoundException;

}
