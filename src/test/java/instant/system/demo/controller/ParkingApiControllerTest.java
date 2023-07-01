package instant.system.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import instant.system.demo.model.Parking;
import instant.system.demo.model.ParkingApi;
import instant.system.demo.service.ParkingApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingApiControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ParkingApiService parkingApiService;

    @InjectMocks
    private ParkingApiController parkingApiController;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(parkingApiController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void getAllParkingApi() throws Exception {
        ParkingApi pa1 = 					new ParkingApi(
                "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom",
                "Poitiers",
                "records"
        );

        ParkingApi pa2 = 					new ParkingApi(
                "https://data.grandpoitiers.fr/api/records/1.0",
                "Poitiers",
                "records"
        );

        List<ParkingApi> parkingApiList = Arrays.asList(pa1, pa2);
        when(parkingApiService.getAllParkingsApi()).thenReturn(parkingApiList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parkingApi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value("Poitiers"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rootNode").value("records"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].url").value("https://data.grandpoitiers.fr/api/records/1.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Poitiers"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rootNode").value("records"));

        verify(parkingApiService, times(1)).getAllParkingsApi();
        verifyNoMoreInteractions(parkingApiService);



    }

    @Test
    void addParking() throws Exception {
        ParkingApi pa1 = 					new ParkingApi(
                "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom",
                "Poitiers",
                "records"
        );

        String content = objectMapper.writeValueAsString(pa1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parkingApi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(parkingApiService, times(1)).addParkingApi(pa1);
        verifyNoMoreInteractions(parkingApiService);
    }

    @Test
    void getNearbyParkings() throws Exception {
        double latitude = 42.564984;
        double longitude = 4.2355454;
        int proximity = 500;
        String city = "Example City";

        List<Parking> nearbyParkings = new ArrayList<>();
        nearbyParkings.add( new Parking("Parking 1", "500", "125", "12", "42.564984", "4.2355454"));
        nearbyParkings.add(new Parking("Parking 2", "400", "180", "120", "42.5444554", "4.254564"));

        when(parkingApiService.getNearbyParkings(anyDouble(), anyDouble(), anyInt(), anyString()))
                .thenReturn(nearbyParkings);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parkingApi/nearby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("proximity", String.valueOf(proximity))
                        .param("city", city))
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

        verify(parkingApiService, times(1)).getNearbyParkings(latitude, longitude, proximity, city);
        verifyNoMoreInteractions(parkingApiService);
    }
}