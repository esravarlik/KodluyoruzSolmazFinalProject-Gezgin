package com.jojo.gezginservice.controller;

import com.jojo.gezginservice.model.Ticket;
import com.jojo.gezginservice.request.TicketRequest;
import com.jojo.gezginservice.response.TicketResponse;
import com.jojo.gezginservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    Logger logger = Logger.getLogger(TicketController.class.getName());

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAll() {
        List<TicketResponse> tickets = ticketService.getAll();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable Integer id) throws Exception {
        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping(value = "/user-tickets/{id}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable("id") Integer userId) throws Exception {
        return ResponseEntity.ok(ticketService.getTicketsByUserId(userId));
    }

    //Admin
    @GetMapping(value = "/{id}/total-price")
    public ResponseEntity<Double> getTotalTicketPrice(@PathVariable("id") Integer id) throws Exception {
        logger.log(Level.INFO, "[TicketController] -- Total ticket price for expedition with id {0}. ", id);
        return ResponseEntity.ok(ticketService.getTotalTicketPrice(id));
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody TicketRequest ticketRequest) throws Exception {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        ticketService.delete(id);
    }

}






