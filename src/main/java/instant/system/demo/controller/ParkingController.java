package instant.system.demo.controller;

import instant.system.demo.model.Parking;
import instant.system.demo.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/parking")
@AllArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;

    @GetMapping
    public List<Parking> getAllParkings() {
        return parkingService.getAllParkings();
    }

    @PostMapping
    public void addParking(@Valid @RequestBody Parking p) {
        parkingService.addParking(p);
    }


}
