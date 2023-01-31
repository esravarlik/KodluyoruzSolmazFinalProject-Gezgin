package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findByCity(String city);
}
