package com.jojo.gezginservice.service;

import com.jojo.gezginservice.converter.TicketConverter;
import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.model.Ticket;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.repository.TicketRepository;
import com.jojo.gezginservice.request.TicketRequest;
import com.jojo.gezginservice.response.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final double TICKET_PRICE = 100.0;

    Logger logger = Logger.getLogger(TicketService.class.getName());

    private final TicketRepository ticketRepository;

    private final TicketConverter converter;

    public TicketService(TicketRepository ticketRepository, TicketConverter converter) {
        this.ticketRepository = ticketRepository;
        this.converter = converter;
    }

    public TicketResponse createTicket(TicketRequest ticketRequest) throws Exception {
        Ticket ticket = converter.convert(ticketRequest);
        ticket.setEnable(false);
        ticket.setPrice(TICKET_PRICE);
        return converter.convert(ticketRepository.save(ticket));
    }

    public List<TicketResponse> getAll() {
        List<Ticket> ticketList = ticketRepository.findAll();
        return converter.convert(ticketList);
    }

    public List<Ticket> getTicketsByUserId(Integer userId) {
        List<Ticket> userTicketList = ticketRepository.findByUserId(userId);
        List<Ticket> userEnableTicketList = userTicketList.stream()
                .filter(ticket -> ticket.getEnable().equals(true)).collect(Collectors.toList());
        return userEnableTicketList;
    }

    public List<Ticket> findByUserIdAndExpeditionId(Integer userId, Integer expeditionId) {
        List<Ticket> ticketList = ticketRepository.findByUserIdAndExpeditionId(userId, expeditionId);
        return ticketList;
    }

    public TicketResponse getTicketById(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.TICKET_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        return converter.convert(ticket);
    }

    //@Cacheable(value = "totalTicketPriceCache", key = "#id")
    public Double getTotalTicketPrice(Integer id) throws Exception {
        List<Ticket> findAllTicket = ticketRepository.findAll();
        List<Ticket> sameExpeditionTickets = findAllTicket.stream()
                .filter(ticket -> ticket.getExpedition().getId().equals(id)).collect(Collectors.toList());

        double totalPrice = sameExpeditionTickets.stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        logger.log(Level.INFO,
                "[TicketService] -- Total ticket price for expedition with id: {0} is {1}",
                new Object[]{id, totalPrice});
        return totalPrice;
    }

    public Ticket setEnableUpdate(Integer id) throws Exception {
        Ticket updated = ticketRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.TICKET_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        updated.setEnable(true);
        ticketRepository.save(updated);
        return updated;
    }


    public void delete(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.TICKET_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        ticketRepository.deleteById(id);
    }
}

