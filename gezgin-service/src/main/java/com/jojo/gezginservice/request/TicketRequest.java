package com.jojo.gezginservice.request;

import com.jojo.gezginservice.model.Expedition;
import com.jojo.gezginservice.model.PassengerInformation;
import com.jojo.gezginservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    private PassengerInformation passengerInformation;

    private Expedition expedition;

    private User user;

}


