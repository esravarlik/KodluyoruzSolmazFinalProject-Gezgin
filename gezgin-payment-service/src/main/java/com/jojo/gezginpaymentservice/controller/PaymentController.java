package com.jojo.gezginpaymentservice.controller;

import com.jojo.gezginpaymentservice.request.PaymentRequest;
import com.jojo.gezginpaymentservice.response.PaymentResponse;
import com.jojo.gezginpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    Logger logger = Logger.getLogger(PaymentController.class.getName());
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest paymentRequest) throws Exception {
        PaymentResponse paymentResponse = paymentService.create(paymentRequest);
        logger.log(Level.INFO, "[PaymentController] -- payment created: " + paymentResponse);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Integer id) throws Exception {
        PaymentResponse paymentResponse = paymentService.getPayment(id);
        logger.log(Level.INFO, "[PaymentController] -- payment: " + paymentResponse);
        return ResponseEntity.ok(paymentResponse);
    }
}
