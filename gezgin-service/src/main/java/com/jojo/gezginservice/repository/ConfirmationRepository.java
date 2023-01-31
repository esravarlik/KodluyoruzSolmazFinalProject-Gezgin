package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Integer> {
}
