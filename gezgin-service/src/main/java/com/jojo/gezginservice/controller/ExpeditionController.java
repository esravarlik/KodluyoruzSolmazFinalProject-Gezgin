package com.jojo.gezginservice.controller;

import com.jojo.gezginservice.model.Expedition;
import com.jojo.gezginservice.request.ExpeditionRequest;
import com.jojo.gezginservice.response.ExpeditionResponse;
import com.jojo.gezginservice.service.ExpeditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/expeditions")
public class ExpeditionController {

    Logger logger = Logger.getLogger(ExpeditionController.class.getName());


    private final ExpeditionService expeditionService;

    public ExpeditionController(ExpeditionService expeditionService) {
        this.expeditionService = expeditionService;
    }

    @GetMapping
    public ResponseEntity<List<ExpeditionResponse>> getAll() {
        logger.log(Level.INFO, "[ExpeditionController] -- expeditions retrieved successfully. ");
        return ResponseEntity.ok(expeditionService.getAll());
    }


    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ExpeditionResponse> getByExpeditionId(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(expeditionService.getByExpeditionId(id));
    }

    @GetMapping(value = "/from/{from}/to/{to}")
    public ResponseEntity<List<ExpeditionResponse>> getByExpeditionLocation(@PathVariable("from") String fromCity,
                                                                            @PathVariable("to") String toCity) throws Exception {
        return ResponseEntity.ok(expeditionService.getByExpeditionLocation(fromCity, toCity));
    }

    @GetMapping(value = "/vehicleTypes/{vehicleType}")
    public ResponseEntity<List<ExpeditionResponse>> getByExpeditionLocation(@PathVariable("vehicleType")
                                                                            String vehicleType) throws Exception {
        return ResponseEntity.ok(expeditionService.getByExpeditionVehicleType(vehicleType));
    }

    @GetMapping(value = "/date/{date}")
    public ResponseEntity<List<Expedition>> getByExpeditionDate(@PathVariable("date")
                                                                        String date) throws Exception {
        return ResponseEntity.ok(expeditionService.getByExpeditionDate(date));
    }

    //Admin
    @GetMapping(value = "/{id}/total-sales")
    public ResponseEntity<Integer> getTotalNumberOfTicketSales(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(expeditionService.getTotalTicketSales(id));
    }

    //Admin
    @PostMapping("/post")
    public ResponseEntity<ExpeditionResponse> create(@RequestBody ExpeditionRequest expeditionRequest) {
        ExpeditionResponse expeditionResponse = expeditionService.create(expeditionRequest);
        logger.log(Level.INFO, "[ExpeditionController] -- expedition created: " + expeditionRequest);
        return ResponseEntity.ok(expeditionResponse);
    }

    //Admin
    @PutMapping("/{id}")
    public ResponseEntity<ExpeditionResponse> update(@PathVariable("id") Integer id,
                                                     @RequestBody ExpeditionRequest expeditionRequest) throws Exception {
        ExpeditionResponse expeditionResponse = expeditionService.update(id, expeditionRequest);
        return ResponseEntity.ok(expeditionResponse);
    }

    //Admin
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        expeditionService.delete(id);
        logger.log(Level.INFO, "[ExpeditionController] -- expedition deleted. ");
    }

}
