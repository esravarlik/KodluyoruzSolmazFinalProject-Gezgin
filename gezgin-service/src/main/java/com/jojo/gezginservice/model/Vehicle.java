package com.jojo.gezginservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jojo.gezginservice.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Builder
@AllArgsConstructor
@Table(name = "vehicle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "vehicle"})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "capacity")
    private Integer capacity;

    public Vehicle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
