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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ConfirmationService {

    private static final int MAX_NUMBER_OF_MEN = 2;
    private static final int INDIVIDUAL_USERS_MAX_TICKET = 5;
    private static final int CORPORATE_USERS_MAX_TICKET = 20;
    Logger logger = Logger.getLogger(ExpeditionService.class.getName());


    private final ConfirmationRepository confirmationRepository;

    private final TicketService ticketService;

    private final PaymentServiceClient paymentServiceClient;

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfiguration rabbitMQConfiguration;


    public ConfirmationService(ConfirmationRepository confirmationRepository, TicketService ticketService,
                               PaymentServiceClient paymentServiceClient, RabbitTemplate rabbitTemplate,
                               RabbitMQConfiguration rabbitMQConfiguration) {
        this.confirmationRepository = confirmationRepository;
        this.ticketService = ticketService;
        this.paymentServiceClient = paymentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfiguration = rabbitMQConfiguration;
    }

    public List<Confirmation> getAll() {
        List<Confirmation> confirmationList = confirmationRepository.findAll();
        return confirmationList;
    }

    public Confirmation getConfirmationById(Integer id) {
        Confirmation confirmation = confirmationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count not found"));
        return confirmation;
    }

    @Transactional
    public Confirmation buy(Confirmation confirmation) throws Exception {
        userCheck(confirmation);

        getMaxNumberOfMen(confirmation);
        getIndividualUsersMaxTicket(confirmation);
        getCorporateUsersMaxTicket(confirmation);

        for (Ticket ticket : new ArrayList<>(confirmation.getTicketList())) {
            ticketService.setEnableUpdate(ticket.getId());
            adequateTicketControl(ticket.getExpedition().getNumberOfTicketsRemaining());
            ticket.getExpedition().setNumberOfTicketsRemaining(ticket.getExpedition().getNumberOfTicketsRemaining() - 1);
        }


        Payment payment = new Payment(confirmation.getTicketList().get(0).getUser().getId(),
                confirmation.getTicketList().get(0).getId(), PaymentType.CREDIT_CARD, "12312312313");
        Payment response = paymentServiceClient.create(payment);

        if (response == null) {
            throw new GeneralException(Message.FAILED_PAYMENT,
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NOT_FOUND);
        }

        confirmation.getTicketList().get(0).getUser().setNotificationType(NotificationType.EMAIL);
        confirmation.getTicketList().get(0).getUser().setDetail("Ticket information sent. ");
        confirmationRepository.save(confirmation);


        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), confirmation.getTicketList().get(0).getUser());
        logger.log(Level.INFO,
                "[GezginService UserService] -- Sent message with user data: {}",
                confirmation.getTicketList().get(0).getUser());

        return confirmation;
    }

    private void adequateTicketControl(Integer ticketNumber) {
        if (ticketNumber < 1) {
            throw new GeneralException(Message.NOT_ENOUGH_TICKET,
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NOT_FOUND);
        }
    }

    private User findUser(Confirmation confirmation) {
        User user = confirmation.getTicketList().get(0).getUser();
        return user;
    }

    private void userCheck(Confirmation confirmation) throws Exception {
        Integer numberOfTicketsByTheUser = Math.toIntExact(confirmation.getTicketList().stream().count());
        if (numberOfTicketsByTheUser > 1) {
            User firstUser = findUser(confirmation);
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
        User firstUser = findUser(confirmation);
        Integer ticketMaleCount = maleCount(confirmation);
        if (UserType.CORPORATE.equals(firstUser.getUserType())) {
            if (ticketMaleCount > MAX_NUMBER_OF_MEN) {
                throw new GeneralException(Message.MAX_TICKET,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND);
            }
        }
    }

    public List<Ticket> getMaxTicketByExpeditionId(Confirmation confirmation) {
        Map<Integer, Long> expeditionIdTicketCount = confirmation.getTicketList().stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getExpedition().getId(), Collectors.counting()));

        Integer maxExpeditionId = expeditionIdTicketCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (maxExpeditionId == null) {
            return Collections.emptyList();
        }

        return confirmation.getTicketList().stream()
                .filter(ticket -> ticket.getExpedition().getId().equals(maxExpeditionId))
                .collect(Collectors.toList());
    }

    private void getIndividualUsersMaxTicket(Confirmation confirmation) throws Exception {
        User firstUser = findUser(confirmation);
        Integer sameExpeditionTicketCount = Math.toIntExact(getMaxTicketByExpeditionId(confirmation).stream().count());

        if (UserType.INDIVIDUAL.equals(firstUser.getUserType())) {
            if (sameExpeditionTicketCount > INDIVIDUAL_USERS_MAX_TICKET) {
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
        User firstUser = findUser(confirmation);
        Integer sameExpeditionTicketCount = Math.toIntExact(getMaxTicketByExpeditionId(confirmation).stream().count());


        if (UserType.CORPORATE.equals(firstUser.getUserType())) {
            logger.log(Level.WARNING,
                    "[ConfirmationService] -- " +
                            "You have exceeded the maximum amount of tickets that can be purchased. ");
            if (sameExpeditionTicketCount > CORPORATE_USERS_MAX_TICKET) {
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
