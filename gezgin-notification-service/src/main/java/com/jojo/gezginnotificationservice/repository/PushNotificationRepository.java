package com.jojo.gezginnotificationservice.repository;


import com.jojo.gezginnotificationservice.model.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository extends JpaRepository<Push, Integer> {
}
