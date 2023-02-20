package com.jojo.gezginservice.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jojo.gezginservice.model.Location;
import com.jojo.gezginservice.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpeditionResponse {

    private Integer id;

    private String company;

    private LocalDate departureDate;

    private Location from;

    private Location to;

    private Vehicle vehicle;

    private Integer numberOfTicketsRemaining;

}
