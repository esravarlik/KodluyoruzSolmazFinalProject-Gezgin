package com.jojo.gezginservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Builder
@AllArgsConstructor
@Table(name = "location")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "location"})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
