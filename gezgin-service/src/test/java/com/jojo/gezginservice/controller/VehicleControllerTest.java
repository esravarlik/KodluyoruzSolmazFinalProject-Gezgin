package com.jojo.gezginservice.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jojo.gezginservice.model.enums.VehicleType;
import com.jojo.gezginservice.response.VehicleResponse;
import com.jojo.gezginservice.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleController.class)
//@SpringBootTest
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;


    @Test
    void it_should_get_all_vehicles() throws Exception{
        //given
        Mockito.when(vehicleService.getVehicles()).thenReturn(getAllVehicleResponses());
        //when
        ResultActions resultActions = mockMvc.perform(get("/vehicles"));
        //then
        resultActions.andExpect(status().isOk());
    }

    private List<VehicleResponse> getAllVehicleResponses() {
        return List.of(getVehicleResponse(1));
    }

    private VehicleResponse getVehicleResponse(int id) {
        return new VehicleResponse(id, VehicleType.BUS,45);
    }

}