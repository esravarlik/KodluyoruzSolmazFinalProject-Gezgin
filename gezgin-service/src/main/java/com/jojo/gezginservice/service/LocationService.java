package com.jojo.gezginservice.service;

import com.jojo.gezginservice.converter.LocationConverter;
import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.model.Location;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.repository.LocationRepository;
import com.jojo.gezginservice.request.LocationRequest;
import com.jojo.gezginservice.response.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LocationService {

    Logger logger = Logger.getLogger(LocationService.class.getName());

    private final LocationRepository locationRepository;

    private final LocationConverter converter;

    public LocationService(LocationRepository locationRepository, LocationConverter converter) {
        this.locationRepository = locationRepository;
        this.converter = converter;
    }

    public LocationResponse createLocation(LocationRequest locationRequest) throws Exception {
        isEmptyCityAndAddress(locationRequest.getCity(), locationRequest.getAddress());
        Location savedLocation = locationRepository.save(converter.convert(locationRequest));
        return converter.convert(savedLocation);
    }

    public List<LocationResponse> getLocations() {
        List<Location> locations = locationRepository.findAll();
        List<LocationResponse> vehicleResponses = converter.convert(locations);
        return vehicleResponses;
    }

    public LocationResponse getByLocation(Integer id) throws Exception {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_LOCATION_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        logger.log(Level.INFO, "Location with id {} was retrieved successfully.", location);
        return converter.convert(location);
    }

    public List<LocationResponse> getByCity(String city) throws Exception {
        List<Location> location = locationRepository.findByCity(city.toUpperCase());
        return converter.convert(location);
    }

    public LocationResponse updateLocation(Integer id, LocationRequest locationRequest) throws Exception {
        Location resultLocation = locationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_LOCATION_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));

        isEmptyCityAndAddress(locationRequest.getCity(), locationRequest.getAddress());

        resultLocation.setCity(locationRequest.getCity());
        resultLocation.setAddress(locationRequest.getAddress());
        return converter.convert(locationRepository.save(resultLocation));

    }

    public void deleteLocation(Integer id) {
        Location deleteLocation = locationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.NO_LOCATION_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        locationRepository.deleteById(id);

    }

    private void isEmptyCityAndAddress(String city, String address) throws Exception {
        if (city.isEmpty() && address.isEmpty()) {
            logger.log(Level.INFO, "[LocationService] -- city: {0}, address: {1} are empty.",
                    new Object[]{city, address});
            throw new GeneralException(Message.CITY_AND_ADDRESS_NOT_NULL,
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.NOT_NULL);
        }

    }
}