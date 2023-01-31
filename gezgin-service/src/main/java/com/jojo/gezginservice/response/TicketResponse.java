package com.jojo.gezginservice.response;

import com.jojo.gezginservice.model.Expedition;
import com.jojo.gezginservice.model.PassengerInformation;
import com.jojo.gezginservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Integer id;

    private Double price;

    private PassengerInformation passengerInformation;

    private Expedition expedition;

    private User user;

    private Boolean isEnable;
}

