package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.PassengerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerInformationRepository extends JpaRepository<PassengerInformation, Integer> {
}
