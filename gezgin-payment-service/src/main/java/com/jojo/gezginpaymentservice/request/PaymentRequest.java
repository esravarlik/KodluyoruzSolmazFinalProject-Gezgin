package com.jojo.gezginpaymentservice.request;

import com.jojo.gezginpaymentservice.model.enums.PaymentType;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Integer userId;

    private String cardNumber;

    private PaymentType paymentType;

    private Integer productId;
}

