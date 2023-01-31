package com.jojo.gezginservice.response;

import com.jojo.gezginservice.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Integer id;

    private VehicleType vehicleType;

    private Integer capacity;

}
