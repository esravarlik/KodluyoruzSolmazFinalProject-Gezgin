package com.jojo.gezginservice.request;

import com.jojo.gezginservice.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {

    private String firstName;

    private String lastName;

    private Gender gender;

    private String IdentificationId;

}
