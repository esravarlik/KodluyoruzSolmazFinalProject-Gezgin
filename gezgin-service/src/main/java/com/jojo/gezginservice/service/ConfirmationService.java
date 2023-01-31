package com.jojo.gezginservice.service;

import com.jojo.gezginservice.client.Payment;
import com.jojo.gezginservice.client.PaymentServiceClient;
import com.jojo.gezginservice.client.enums.PaymentType;
import com.jojo.gezginservice.config.RabbitMQConfiguration;
import com.jojo.gezginservice.config.enums.NotificationType;
import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.model.Confirmation;
import com.jojo.gezginservice.model.Ticket;
import com.jojo.gezginservice.model.User;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.model.enums.Gender;
import com.jojo.gezginservice.model.enums.UserType;
import com.jojo.gezginservice.repository.ConfirmationRepository;
import com.jojo.gezginservice.request.TicketRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ConfirmationService {

    private static final int MAX_NUMBER_OF_MEN = 2;
    private static final int INDIVIDUAL_USERS_MAX_TICKET = 5;
    private static final int CORPORATE_USERS_MAX_TICKET = 20;
    Logger logger = Logger.getLogger(ExpeditionService.class.getName());

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfiguration rabbitMQConfiguration;

    public List<Confirmation> getAll() {
        List<Confirmation> confirmationList = confirmationRepository.findAll();
        return confirmationList;
    }

    public Confirmation getOrderById(Integer id) {
        Confirmation confirmation = confirmationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count not found"));
        return confirmation;
    }

    @Transactional
    public Confirmation buy(Confirmation confirmation) throws Exception {
        userCheck(confirmation);

        List<Ticket> buyTickets = ticketService
                .findByUserIdAndExpeditionId(confirmation.getTicketList().get(0).getUser().getId(),
                        confirmation.getTicketList().get(0).getExpedition().getId());

        getMaxNumberOfMen(confirmation);
        getIndividualUsersMaxTicket(confirmation);
        getCorporateUsersMaxTicket(confirmation);

        for (Ticket ticket : buyTickets) {
            ticketService.setEnableUpdate(ticket.getId());
        }

        Payment payment = new Payment(confirmation.getTicketList().get(0).getUser().getId(),
                confirmation.getId(), PaymentType.CREDIT_CARD, "12312312313");
        Payment response = paymentServiceClient.create(payment);

        confirmation.getTicketList().get(0).getUser().setNotificationType(NotificationType.EMAIL);
        confirmationRepository.save(confirmation);

        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), confirmation.getTicketList().get(0).getUser());
        logger.log(Level.INFO,
                "[GezginService UserService] -- Sent message with user data: {}",
                confirmation.getTicketList().get(0).getUser());

        return confirmation;
    }

    private void userCheck(Confirmation confirmation) throws Exception {
        Integer numberOfTicketsByTheUser = Math.toIntExact(confirmation.getTicketList().stream().count());
        if (numberOfTicketsByTheUser > 1) {
            User firstUser = confirmation.getTicketList().get(0).getUser();
            boolean anyDifferent = confirmation.getTicketList().stream()
                    .anyMatch(ticket -> !ticket.getUser().equals(firstUser));
            if (anyDifferent) {
                throw new GeneralException(Message.NOT_SAME_USER,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.INVALID);
            }
        }
    }

    private Integer maleCount(Confirmation confirmation) {
        List<Ticket> filterTicketList = confirmation.getTicketList().stream()
                .filter(ticket -> (Gender.MALE).equals(ticket.getPassengerInformation().getGender()))
                .collect(Collectors.toList());

        return Math.toIntExact(filterTicketList.stream().count());
    }

    private void getMaxNumberOfMen(Confirmation confirmation) throws Exception {
        User firstUser = confirmation.getTicketList().get(0).getUser();
        Integer ticketMaleCount = maleCount(confirmation);
        if (UserType.CORPORATE.equals(firstUser.getUserType())) {
            if (ticketMaleCount > MAX_NUMBER_OF_MEN) {
                throw new GeneralException(Message.MAX_TICKET,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND);
            }
        }
    }

    private Integer filteredCount(User user, TicketRequest ticketRequest) {
        List<Ticket> filtered =
                ticketService.findByUserIdAndExpeditionId(user.getId(),
                        ticketRequest.getExpedition().getId());
        return Math.toIntExact(filtered.stream().count());
    }

    private void getIndividualUsersMaxTicket(Confirmation confirmation) throws Exception {
        User firstUser = confirmation.getTicketList().get(0).getUser();
        Integer ticketCount = Math.toIntExact(confirmation.getTicketList().stream().count());
        if (UserType.INDIVIDUAL.equals(firstUser.getUserType())) {
            if (ticketCount > INDIVIDUAL_USERS_MAX_TICKET) {
                logger.log(Level.WARNING,
                        "[ConfirmationService] -- " +
                                "You have exceeded the maximum amount of tickets that can be purchased. ");
                throw new GeneralException(Message.MAX_TICKET,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND);
            }
        }
    }

    private void getCorporateUsersMaxTicket(Confirmation confirmation) throws Exception {
        User firstUser = confirmation.getTicketList().get(0).getUser();
        Integer ticketCount = Math.toIntExact(confirmation.getTicketList().stream().count());

        if (UserType.CORPORATE.equals(firstUser.getUserType())) {
            logger.log(Level.WARNING,
                    "[ConfirmationService] -- " +
                            "You have exceeded the maximum amount of tickets that can be purchased. ");
            if (ticketCount > CORPORATE_USERS_MAX_TICKET) {
                throw new GeneralException(Message.MAX_TICKET,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND);
            }
        }
    }

    public void delete(Integer id) {
        Confirmation deleted = confirmationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.VEHICLE_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        confirmationRepository.delete(deleted);
    }
}
