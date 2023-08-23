package instant.system.demo.service;

import instant.system.demo.model.Parking;
import instant.system.demo.repository.ParkingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceTest {

    @InjectMocks
    private ParkingService parkingService;

    @Mock
    private ParkingRepository parkingRepository;

    @Test
    public void testGetAllParkings() {
        List<Parking> mockParkings = List.of(new Parking("Location A","test","test","test","test", "test"), new Parking("Location B", "test", "test","test","test","test"));
        when(parkingRepository.findAll()).thenReturn(mockParkings);

        List<Parking> retrievedParkings = parkingService.getAllParkings();

        assertThat(retrievedParkings).isEqualTo(mockParkings);
        verify(parkingRepository, times(1)).findAll();
    }

    @Test
    public void testAddParking() {
        Parking parking = new Parking("Location A","test","test","test","test", "test");

        parkingService.addParking(parking);

        verify(parkingRepository, times(1)).save(parking);
    }

}