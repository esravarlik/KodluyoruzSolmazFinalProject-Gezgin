package com.jojo.gezginservice.controller;

import com.jojo.gezginservice.request.LocationRequest;
import com.jojo.gezginservice.response.LocationResponse;
import com.jojo.gezginservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/locations")
public class LocationController {

    Logger logger = Logger.getLogger(LocationController.class.getName());

    @Autowired
    LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody LocationRequest locationRequest) throws Exception {
        LocationResponse locationResponse = locationService.createLocation(locationRequest);
        logger.log(Level.INFO, "[LocationController] -- location created: " + locationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationResponse);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getLocations() {
        List<LocationResponse> locations = locationService.getLocations();
        logger.log(Level.INFO, "[LocationController] -- locations retrieved successfully. ");
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getByLocation(@PathVariable("id") Integer id) throws Exception {
        LocationResponse locationResponse = locationService.getByLocation(id);
        logger.log(Level.INFO, "[LocationController] -- location with id {0} retrieved. ", new Object[]{id});
        return ResponseEntity.ok(locationResponse);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<LocationResponse>> getCityList(@PathVariable("city")
                                                              String city) throws Exception {
        List<LocationResponse> locationResponse = locationService.getByCity(city);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(locationResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable("id") Integer id,
                                                           @RequestBody LocationRequest locationRequest) throws Exception {
        LocationResponse locationResponse = locationService.updateLocation(id, locationRequest);
        return ResponseEntity.ok(locationResponse);

    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable("id") Integer id) throws Exception {
        logger.log(Level.INFO, "[LocationController] -- location deleted. ");
        locationService.deleteLocation(id);
    }
}
