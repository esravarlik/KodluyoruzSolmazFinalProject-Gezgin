package com.jojo.gezginpaymentservice.response;

import com.jojo.gezginpaymentservice.model.enums.PaymentType;
import lombok.*;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Integer id;

    private Integer userId;

    private PaymentType paymentType;

    private Integer productId;
}
