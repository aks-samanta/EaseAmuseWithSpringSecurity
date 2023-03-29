package com.EaseAmuse.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EaseAmuse.exceptions.ResourceNotFoundException;
import com.EaseAmuse.exceptions.UnauthorisedException;
import com.EaseAmuse.models.Customer;
import com.EaseAmuse.models.DailyActivity;
import com.EaseAmuse.models.Ticket;
import com.EaseAmuse.models.TicketStatus;
import com.EaseAmuse.payloads.TicketDto;
import com.EaseAmuse.repositories.CustomerRepo;
import com.EaseAmuse.repositories.DailyActivityRepo;
import com.EaseAmuse.repositories.TicketRepo;

@Service
public class TicketServicesImpl implements TicketServices {

	@Autowired
	private DailyActivityRepo dailyActivityRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TicketRepo ticketRepo;

	@Override
	public TicketDto cancelTicket(Integer customerId, Integer ticketId) throws ResourceNotFoundException {

		Ticket foundTicket = this.ticketRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket", "Ticket ID", ticketId.toString()));

		if (foundTicket.getCustomer().getCustomerId() == customerId) {
			if (foundTicket.getTicketStatus() == TicketStatus.CANCELLED) {
				throw new UnauthorisedException("Ticket Already Cancelled");
			}
			foundTicket.setTicketStatus(TicketStatus.CANCELLED);

		} else {
			throw new UnauthorisedException("You are only authorosed to cancel your tickets !");
		}

		foundTicket.getDailyActivity()
				.setSlotsRemaining(foundTicket.getDailyActivity().getSlotsRemaining() + foundTicket.getNoOfPerson());

		this.dailyActivityRepo.save(foundTicket.getDailyActivity());
		TicketDto toutDto = this.modelMapper.map(foundTicket, TicketDto.class);
		toutDto.setDailyActivitiesId(foundTicket.getDailyActivity().getDailyActivityId());
		return toutDto;
	}

	@Override
	public TicketDto createTicket(Integer customerId, TicketDto ticketDto) throws ResourceNotFoundException {

		DailyActivity dailyActivity = dailyActivityRepo.findById(ticketDto.getDailyActivitiesId())
				.orElseThrow(() -> new ResourceNotFoundException("DailyActivity", "DailyActivity ID",
						ticketDto.getDailyActivitiesId().toString()));

		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer ID", customerId.toString()));

		if (dailyActivity.getSlotsRemaining() >= ticketDto.getNoOfPerson()) {

			Ticket newTicket = new Ticket();
			newTicket.setAmount(dailyActivity.getActivity().getCharges() * ticketDto.getNoOfPerson());
			newTicket.setDailyActivity(dailyActivity);
			newTicket.setNoOfPerson(ticketDto.getNoOfPerson());
			newTicket.setTicketStatus(TicketStatus.PENDING);
			newTicket.setCustomer(customer);

			dailyActivity.setSlotsRemaining(dailyActivity.getSlotsRemaining() - ticketDto.getNoOfPerson());
			dailyActivity.getTickets().add(newTicket);

			DailyActivity updateDailyActivity = this.dailyActivityRepo.save(dailyActivity);

			Ticket savedTicket = updateDailyActivity.getTickets().get(updateDailyActivity.getTickets().size() - 1);
			TicketDto ticketOutDto = this.modelMapper.map(savedTicket, TicketDto.class);
			ticketOutDto.setDailyActivitiesId(dailyActivity.getDailyActivityId());
			return ticketOutDto;
		} else {
			throw new UnauthorisedException("Available slots is less than " + ticketDto.getNoOfPerson());
		}
	}
}
