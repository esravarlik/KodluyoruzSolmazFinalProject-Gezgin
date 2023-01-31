package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Integer> {

    List<Expedition> findByFromCityIgnoreCaseAndToCityIgnoreCase(String fromCity, String toCity);

}
