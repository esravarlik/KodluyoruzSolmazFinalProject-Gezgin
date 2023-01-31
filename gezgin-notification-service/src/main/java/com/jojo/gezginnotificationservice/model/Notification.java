package com.jojo.gezginnotificationservice.model;

import com.jojo.gezginnotificationservice.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@MappedSuperclass
public class Notification {
    @CreationTimestamp
    private LocalDateTime createdDate;

    private String message;

    private String email;

    private String phoneNumber;

    private Integer deviceId;

    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    public Notification() {

    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
