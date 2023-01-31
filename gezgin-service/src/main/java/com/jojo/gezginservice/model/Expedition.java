package com.jojo.gezginservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@ToString
@Entity
@Table(name = "expedition")
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "expedition"})
public class Expedition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "company")
    private String company;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime departureDate;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Location from;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Location to;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "number_of_tickets_remaining")
    private Integer numberOfTicketsRemaining;

    public Expedition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public Integer getNumberOfTicketsRemaining() {
        return numberOfTicketsRemaining;
    }

    public void setNumberOfTicketsRemaining(Integer numberOfTicketsRemaining) {
        this.numberOfTicketsRemaining = numberOfTicketsRemaining;
    }

}
