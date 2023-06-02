package instant.system.demo.service;

import instant.system.demo.model.ParkingApiDataStructure;
import instant.system.demo.repository.ParkingApiDataStructureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ParkingApiDataStrcutreService {
    private final ParkingApiDataStructureRepository parkingApiDataStructureRepository;

    public List<ParkingApiDataStructure> getAllParkingApiDataStructure() {
        return parkingApiDataStructureRepository.findAll();
    }

    public void addParkingApiDataStructure(ParkingApiDataStructure p) {
        parkingApiDataStructureRepository.save(p);
    }

}
