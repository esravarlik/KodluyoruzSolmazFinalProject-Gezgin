package com.jojo.gezginservice.controller;

import com.jojo.gezginservice.request.VehicleRequest;
import com.jojo.gezginservice.response.VehicleResponse;
import com.jojo.gezginservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    Logger logger = Logger.getLogger(VehicleController.class.getName());

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@RequestBody VehicleRequest vehicleRequest) throws Exception {
        VehicleResponse status = vehicleService.create(vehicleRequest);
        logger.log(Level.INFO, "[VehicleController] -- vehicle created: " + vehicleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles() {
        List<VehicleResponse> vehicles = vehicleService.getVehicles();
        logger.log(Level.INFO, "[VehicleController] -- vehicles retrieved successfully. ");
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable("id") Integer id) throws Exception {
        VehicleResponse vehicleResponse = vehicleService.getVehicle(id);
        logger.log(Level.INFO, "[VehicleController] -- vehicle with id {0} retrieved. ", new Object[]{id});
        return ResponseEntity.ok(vehicleResponse);
    }

    @GetMapping("/vehicleTypes/{vehicleType}")
    public ResponseEntity<List<VehicleResponse>> getVehicleType(@PathVariable("vehicleType")
                                                                String vehicleType) throws Exception {
        List<VehicleResponse> vehicleResponses = vehicleService.getVehicleType(vehicleType);
        logger.log(Level.INFO, "[VehicleController] -- vehicles of type {0} retrieved. ", vehicleType);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehicleResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable("id") Integer id,
                                                         @RequestBody VehicleRequest vehicleRequest) throws Exception {
        VehicleResponse vehicleResponse = vehicleService.updateVehicle(id, vehicleRequest);
        logger.log(Level.INFO, "[VehicleController] -- vehicle type: {0}, capacity: {1} updated. ",
                new Object[]{vehicleRequest.getVehicleType(), vehicleRequest.getCapacity()});
        return ResponseEntity.ok(vehicleResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable("id") Integer id) throws Exception {
        logger.log(Level.INFO, "[VehicleController] -- vehicle deleted. ");
        vehicleService.deleteVehicle(id);
    }
}
