package instant.system.demo.repository;

import instant.system.demo.model.ParkingApi;
import instant.system.demo.model.ParkingApiDataStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingApiRepository
        extends JpaRepository<ParkingApi, Long> {
    @Query("SELECT pa FROM ParkingApi pa WHERE pa.city = ?1")
    ParkingApi findByCity(String city);

    @Query("SELECT pads FROM ParkingApiDataStructure pads WHERE pads.parkingApi.id = ?1")
    List<ParkingApiDataStructure> findAllById(Long id);
}
