package com.jojo.gezginnotificationservice.listener;

import com.jojo.gezginnotificationservice.model.Email;
import com.jojo.gezginnotificationservice.model.Notification;
import com.jojo.gezginnotificationservice.model.Push;
import com.jojo.gezginnotificationservice.model.SMS;
import com.jojo.gezginnotificationservice.model.enums.NotificationType;
import com.jojo.gezginnotificationservice.repository.EmailNotificationRepository;
import com.jojo.gezginnotificationservice.repository.PushNotificationRepository;
import com.jojo.gezginnotificationservice.repository.SMSNotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationListener {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    @Autowired
    private SMSNotificationRepository smsNotificationRepository;


    @RabbitListener(queues = "gezgin.notification")
    public void notificationListener(Notification notification) {
        if (NotificationType.EMAIL.equals(notification.getNotificationType())) {
            Email resultEmail = new Email();
            resultEmail.setMessage("The user is registered in the system.");
            resultEmail.setEmail(notification.getEmail());
            resultEmail.setCreatedDate(LocalDateTime.now());
            resultEmail.setNotificationType(NotificationType.EMAIL);

            emailNotificationRepository.save(resultEmail);

        } else if (NotificationType.SMS.equals(notification.getNotificationType())) {
            SMS resultSMS = new SMS();
            resultSMS.setMessage("Ticket details have been sent as SMS.");
            resultSMS.setPhoneNumber(notification.getPhoneNumber());
            resultSMS.setDetail(notification.getDetail());
            resultSMS.setNotificationType(NotificationType.SMS);

            smsNotificationRepository.save(resultSMS);

        } else if (NotificationType.PUSH.equals(notification.getNotificationType())) {
            Push resultPush = new Push();
            resultPush.setMessage("Push notification sent. ");
            resultPush.setDetail(notification.getDetail());
            resultPush.setNotificationType(NotificationType.PUSH);

            pushNotificationRepository.save(resultPush);
        }
    }
}
