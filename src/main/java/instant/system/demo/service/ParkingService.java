package instant.system.demo.service;

import instant.system.demo.model.Parking;
import instant.system.demo.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public void addParking(Parking p) {
        parkingRepository.save(p);
    }

}
