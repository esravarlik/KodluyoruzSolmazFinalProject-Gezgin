package com.jojo.gezginpaymentservice.converter;

import com.jojo.gezginpaymentservice.model.Payment;
import com.jojo.gezginpaymentservice.request.PaymentRequest;
import com.jojo.gezginpaymentservice.response.PaymentResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConverter {

    public PaymentResponse convert(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentType(payment.getPaymentType())
                .productId(payment.getProductId())
                .userId(payment.getUserId())
                .build();
    }

    public Payment convert(PaymentRequest request) {
        return Payment.builder()
                .cardNumber(request.getCardNumber())
                .paymentType(request.getPaymentType())
                .userId(request.getUserId())
                .productId(request.getProductId())
                .build();
    }

}
