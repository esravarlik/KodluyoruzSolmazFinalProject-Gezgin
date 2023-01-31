package com.jojo.gezginservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassengerRequest {

    private String firstName;

    private String lastName;

    private String IdentificationId;

}
