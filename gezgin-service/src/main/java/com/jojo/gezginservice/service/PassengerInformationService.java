package com.jojo.gezginservice.service;

import com.jojo.gezginservice.converter.PassengerConverter;
import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.model.PassengerInformation;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.repository.PassengerInformationRepository;
import com.jojo.gezginservice.request.PassengerRequest;
import com.jojo.gezginservice.request.UpdatePassengerRequest;
import com.jojo.gezginservice.response.PassengerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PassengerInformationService {

    Logger logger = Logger.getLogger(PassengerInformationService.class.getName());

    @Autowired
    private PassengerInformationRepository passengerInformationRepository;

    @Autowired
    private PassengerConverter converter;

    public PassengerResponse create(PassengerRequest passenger) throws Exception {
        isEmptyFirstNameAndLastName(passenger.getFirstName(), passenger.getLastName());
        validateIdNumber(passenger.getIdentificationId());
        PassengerInformation result = passengerInformationRepository.save(converter.convert(passenger));
        return converter.convert(result);
    }

    public List<PassengerResponse> getAll() {
        List<PassengerInformation> passengerInformationList = passengerInformationRepository.findAll();
        return converter.convert(passengerInformationList);
    }

    public PassengerResponse getPassenger(Integer id) throws Exception {
        PassengerInformation passengerInformation = passengerInformationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_PASSENGER_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        return converter.convert(passengerInformation);

    }

    public PassengerResponse update(Integer id, UpdatePassengerRequest updatePassengerRequest) throws Exception {
        PassengerInformation resultPassenger = passengerInformationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_PASSENGER_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));

        isEmptyFirstNameAndLastName(updatePassengerRequest.getFirstName(), updatePassengerRequest.getLastName());
        validateIdNumber(updatePassengerRequest.getIdentificationId());

        resultPassenger.setFirstName(updatePassengerRequest.getFirstName());
        resultPassenger.setLastName(updatePassengerRequest.getLastName());
        passengerInformationRepository.save(resultPassenger);

        return converter.convert(resultPassenger);
    }

    public void delete(Integer id) throws Exception {
        PassengerInformation deleted = passengerInformationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_PASSENGER_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        passengerInformationRepository.deleteById(deleted.getId());
    }

    private void isEmptyFirstNameAndLastName(String firstName, String lastName) throws Exception {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new GeneralException(Message.NOT_BLANK_FIRST_AND_LASTNAME,
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NOT_BLANK);
        }
    }

    private void validateIdNumber(String idNumber) throws Exception {
        if (idNumber.length() != 11) {
            logger.log(Level.WARNING, "[PassengerInformationService] -- First name and last name cannot be empty. ");
            throw new GeneralException(Message.IDENTITY_CHECK,
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID);
        }
        try {
            long number = Long.parseLong(idNumber);
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "[PassengerInformationService] -- Id number must be a number. ");
            throw new GeneralException(Message.IDENTITY_CHECK_NUMBER,
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID);
        }
    }

}