package com.jojo.gezginservice.request;

import com.jojo.gezginservice.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    private VehicleType vehicleType;

    private Integer capacity;
}
