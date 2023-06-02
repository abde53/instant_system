package instant.system.demo.repository;

import instant.system.demo.model.ParkingApiDataStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingApiDataStructureRepository extends JpaRepository<ParkingApiDataStructure, Long> {
}
