package instant.system.demo.config;

import instant.system.demo.model.ParkingApi;
import instant.system.demo.model.ParkingApiDataStructure;
import instant.system.demo.service.ParkingApiDataStrcutreService;
import instant.system.demo.service.ParkingApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParkingApiDataStructureConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            ParkingApiService p,
            ParkingApiDataStrcutreService padss) {
        return args -> {

            ParkingApi pa1 = 					new ParkingApi(
                    "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom",
                    "Poitiers",
                    "records"
            );
            p.addParkingApi(pa1);

            padss.addParkingApiDataStructure(  new ParkingApiDataStructure("/fields/nom", "name", pa1));
            padss.addParkingApiDataStructure(  new ParkingApiDataStructure("/fields/capacite", "capacity", pa1));
            padss.addParkingApiDataStructure(  new ParkingApiDataStructure("/fields/geo_point_2d/0", "latitude", pa1));
            padss.addParkingApiDataStructure(  new ParkingApiDataStructure("/fields/geo_point_2d/1", "longitude", pa1));
            padss.addParkingApiDataStructure(  new ParkingApiDataStructure("/fields/places", "available_place", pa1));
        };
    }
}
