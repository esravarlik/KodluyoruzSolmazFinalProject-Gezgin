package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.Expedition;
import com.jojo.gezginservice.response.ExpeditionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Integer> {

    List<Expedition> findByFromCityIgnoreCaseAndToCityIgnoreCase(String fromCity, String toCity);


}
