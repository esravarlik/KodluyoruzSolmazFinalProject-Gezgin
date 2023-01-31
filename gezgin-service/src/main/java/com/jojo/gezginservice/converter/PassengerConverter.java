package com.jojo.gezginservice.converter;

import com.jojo.gezginservice.model.PassengerInformation;
import com.jojo.gezginservice.request.PassengerRequest;
import com.jojo.gezginservice.request.UpdatePassengerRequest;
import com.jojo.gezginservice.response.PassengerResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PassengerConverter {

    public PassengerResponse convert(PassengerInformation passengerInformation) {
        return PassengerResponse.builder()
                .id(passengerInformation.getId())
                .firstName(passengerInformation.getFirstName())
                .lastName(passengerInformation.getLastName())
                .gender(passengerInformation.getGender())
                .IdentificationId(passengerInformation.getIdentificationId())
                .build();
    }

    public PassengerInformation convert(PassengerRequest passengerRequest) {
        return PassengerInformation.builder()
                .firstName(passengerRequest.getFirstName())
                .lastName(passengerRequest.getLastName())
                .gender(passengerRequest.getGender())
                .IdentificationId(passengerRequest.getIdentificationId())
                .build();

    }

    public PassengerInformation convert(UpdatePassengerRequest updatePassengerRequest) {
        return PassengerInformation.builder()
                .firstName(updatePassengerRequest.getFirstName())
                .lastName(updatePassengerRequest.getLastName())
                .IdentificationId(updatePassengerRequest.getIdentificationId())
                .build();
    }

    public List<PassengerResponse> convert(List<PassengerInformation> passengerInformationList) {
        List<PassengerResponse> passengerResponses = new ArrayList<>();
        passengerInformationList.stream().forEach(passenger -> passengerResponses.add(convert(passenger)));
        return passengerResponses;
    }

}
