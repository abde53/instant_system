package instant.system.demo.controller;

import instant.system.demo.dto.ParkingApiDto;
import instant.system.demo.dto.ParkingApiMapper;
import instant.system.demo.exception.NoNearbyParkingFoundException;
import instant.system.demo.model.Parking;
import instant.system.demo.model.ParkingApi;
import instant.system.demo.service.ParkingApiService;
import io.swagger.annotations.ApiOperation;
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
    private final ParkingApiMapper parkingApiMapper; // Inject the mapper

    @GetMapping
    public List<ParkingApi> getAllParkingApi() {
        LOGGER.info("inside getAllParkingAPI");
        return parkingApiService.getAllParkingsApi();
    }

    @PostMapping
    public void addParking(@Valid @RequestBody ParkingApiDto p) {
        ParkingApi parkingApi = parkingApiMapper.toEntity(p);
        parkingApiService.addParkingApi(parkingApi);
    }

    // Post pour récuperer les parking à proximité
    @PostMapping("/nearby")
    @ApiOperation(value = "Get all neurby parking based on current user position", response = Parking.class,notes = "Returns list of parkings")
    public List<Parking> getNearbyParkings(@PathVariable(value = "latitude of user") @RequestParam("latitude") double latitude,@PathVariable(value = "longitude of user") @RequestParam("longitude") double longitude,
                                           @PathVariable(value = "rayonne of proximity in meter") @RequestParam("proximity") int proximity, @PathVariable(value = "city name") @RequestParam("city") String city) {
        LOGGER.info("inside getNearbyParkings latitude : {}, longitude : {}", latitude, longitude);
        List<Parking> lp = parkingApiService.getNearbyParkings(latitude, longitude, proximity, city);
        if(lp.isEmpty())
            throw new NoNearbyParkingFoundException("No parking found");
        return lp;
    }
}
