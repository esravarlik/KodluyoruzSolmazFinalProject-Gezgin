package com.jojo.gezginservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jojo.gezginservice.config.enums.NotificationType;
import com.jojo.gezginservice.model.enums.Role;
import com.jojo.gezginservice.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@Builder
@Table(name = "users")
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String detail;

    public User() {
    }

    public User(Integer id, String email, String password, UserType userType, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
