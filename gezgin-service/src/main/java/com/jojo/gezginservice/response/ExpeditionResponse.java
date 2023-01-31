package com.jojo.gezginservice.response;

import com.jojo.gezginservice.model.Location;
import com.jojo.gezginservice.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpeditionResponse {

    private Integer id;

    private String company;

    private LocalDateTime departureDate;

    private Location from;

    private Location to;

    private Vehicle vehicle;

    private Integer numberOfTicketsRemaining;

}
