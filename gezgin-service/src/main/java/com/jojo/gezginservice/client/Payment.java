package com.jojo.gezginservice.client;

import com.jojo.gezginservice.client.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Integer userId;

    private Integer productId;

    private PaymentType paymentType;

    private String cardNumber;

}
