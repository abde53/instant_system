package instant.system.demo.repository;

import instant.system.demo.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository
        extends JpaRepository<Parking, Long> {

}
