package com.jojo.gezginpaymentservice.service;

import com.jojo.gezginpaymentservice.converter.PaymentConverter;
import com.jojo.gezginpaymentservice.model.Payment;
import com.jojo.gezginpaymentservice.repository.PaymentRepository;
import com.jojo.gezginpaymentservice.request.PaymentRequest;
import com.jojo.gezginpaymentservice.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter converter;

    public PaymentResponse create(PaymentRequest paymentRequest) throws Exception {
        if (paymentRequest.getUserId() != null && paymentRequest.getProductId() != null) {
            Payment toSave = paymentRepository.save(converter.convert(paymentRequest));
            return converter.convert(toSave);
        }
        throw new Exception("PaymentRequest userId and productId cannot be null. ");
    }

    public PaymentResponse getPayment(Integer id) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        return converter.convert(payment);
    }
}
