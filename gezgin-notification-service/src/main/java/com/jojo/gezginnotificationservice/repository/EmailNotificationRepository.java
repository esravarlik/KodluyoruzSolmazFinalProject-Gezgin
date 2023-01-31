package com.jojo.gezginnotificationservice.repository;

import com.jojo.gezginnotificationservice.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<Email, Integer> {
}
