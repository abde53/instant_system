package instant.system.demo.controller;

import instant.system.demo.exception.NoNearbyParkingFoundException;
import instant.system.demo.model.Parking;
import instant.system.demo.model.ParkingApi;
import instant.system.demo.service.ParkingApiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/parkingApi")
@AllArgsConstructor
public class ParkingApiController {
    private final ParkingApiService parkingApiService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingApiController.class);

    @GetMapping
    public List<ParkingApi> getAllParkingApi() {
        LOGGER.info("inside getAllParkingAPI");
        return parkingApiService.getAllParkingsApi();
    }

    @PostMapping
    public void addParking(@Valid @RequestBody ParkingApi p) {
        parkingApiService.addParkingApi(p);
    }

    // Post pour récuperer les parking à proximité
    @PostMapping("/nearby")
    public List<Parking> getNearbyParkings(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude,
                                           @RequestParam("proximity") int proximity, @RequestParam("city") String city) {
        LOGGER.info("inside getNearbyParkings latitude : "+ latitude+", longitude : "+longitude);
        List<Parking> lp = parkingApiService.getNearbyParkings(latitude, longitude, proximity, city);
        if(lp.isEmpty())
            throw new NoNearbyParkingFoundException("No parking found");
        return lp;
    }
}
