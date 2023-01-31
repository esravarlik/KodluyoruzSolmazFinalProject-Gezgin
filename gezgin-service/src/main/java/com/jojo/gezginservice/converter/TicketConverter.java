package com.jojo.gezginservice.converter;

import com.jojo.gezginservice.model.Ticket;
import com.jojo.gezginservice.request.TicketRequest;
import com.jojo.gezginservice.response.TicketResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketConverter {

    public TicketResponse convert(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .price(ticket.getPrice())
                .passengerInformation(ticket.getPassengerInformation())
                .expedition(ticket.getExpedition())
                .user(ticket.getUser())
                .isEnable(ticket.getEnable())
                .build();

    }

    public Ticket convert(TicketRequest ticketRequest) {
        return Ticket.builder()
                .passengerInformation(ticketRequest.getPassengerInformation())
                .expedition(ticketRequest.getExpedition())
                .user(ticketRequest.getUser())
                .build();
    }

    public List<TicketResponse> convert(List<Ticket> tickets) {
        List<TicketResponse> ticketResponses = new ArrayList<>();
        tickets.stream().forEach(ticket -> ticketResponses.add(convert(ticket)));
        return ticketResponses;
    }
}
