package com.jojo.gezginnotificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "email")
@AllArgsConstructor
@NoArgsConstructor
public class Email extends Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

}
