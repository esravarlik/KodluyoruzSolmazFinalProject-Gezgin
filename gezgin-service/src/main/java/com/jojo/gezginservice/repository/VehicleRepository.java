package com.jojo.gezginservice.repository;

import com.jojo.gezginservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query(value = "select * from vehicle e where e.vehicle_type = :vehicleType", nativeQuery = true)
    List<Vehicle> findByVehicleType(String vehicleType);

}
