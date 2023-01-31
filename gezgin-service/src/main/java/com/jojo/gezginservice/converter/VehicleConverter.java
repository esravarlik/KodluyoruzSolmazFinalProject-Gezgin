package com.jojo.gezginservice.converter;

import com.jojo.gezginservice.model.Vehicle;
import com.jojo.gezginservice.request.VehicleRequest;
import com.jojo.gezginservice.response.VehicleResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleConverter {

    public VehicleResponse convert(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vehicleType(vehicle.getVehicleType())
                .capacity(vehicle.getCapacity())
                .build();
    }

    public Vehicle convert(VehicleRequest request) {
        return Vehicle.builder()
                .vehicleType(request.getVehicleType())
                .capacity(request.getCapacity())
                .build();
    }

    public List<VehicleResponse> convert(List<Vehicle> vehicles) {
        List<VehicleResponse> vehicleResponses = new ArrayList<>();
        vehicles.stream().forEach(vehicle -> vehicleResponses.add(convert(vehicle)));
        return vehicleResponses;
    }

}
