package instant.system.demo.controller;

import instant.system.demo.controller.ParkingController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import instant.system.demo.model.Parking;
import instant.system.demo.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class ParkingControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(parkingController).build();
    }

    @Test
    void getAllParkings() throws Exception {
        Parking parking1 = new Parking("Parking 1", "500", "125", "12", "42.564984", "4.2355454");
        Parking parking2 = new Parking("Parking 2", "400", "180", "120", "42.5444554", "4.254564");
        List<Parking> parkings = Arrays.asList(parking1, parking2);

        when(parkingService.getAllParkings()).thenReturn(parkings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Parking 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value("500"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].available_place").value("125"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].distance").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].latitude").value("42.564984"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].longitude").value("4.2355454"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Parking 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].available_place").value("180"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].distance").value("120"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].latitude").value("42.5444554"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].longitude").value("4.254564"));

        verify(parkingService, times(1)).getAllParkings();
        verifyNoMoreInteractions(parkingService);
    }



    @Test
    void addParking() throws Exception {
        Parking parking = new Parking("Parking 1", "500", "125", "12", "42.564984", "4.2355454");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Parking 1\",\"capacity\":\"500\",\"available_place\":\"125\",\"distance\":\"12\",\"latitude\":\"42.564984\",\"longitude\":\"4.2355454\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(parkingService, times(1)).addParking(parking);
        verifyNoMoreInteractions(parkingService);
    }

}