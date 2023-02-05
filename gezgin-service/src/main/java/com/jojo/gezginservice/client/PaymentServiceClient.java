package com.jojo.gezginservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gezgin-payment", url = "http://localhost:7075")
public interface PaymentServiceClient {

    @PostMapping(value = "/payments")
    Payment create(@RequestBody Payment payment);

    @GetMapping("/{id}")
    Payment getById(@PathVariable Integer id);

}
