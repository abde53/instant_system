package instant.system.demo.config;

import instant.system.demo.model.ParkingApi;
import instant.system.demo.model.ParkingApiDataStructure;
import instant.system.demo.model.Role;
import instant.system.demo.model.User;
import instant.system.demo.service.ParkingApiDataStrcutreService;
import instant.system.demo.service.ParkingApiService;
import instant.system.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ParkingApiDataStructureConfig {

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ParkingApiService p,
            ParkingApiDataStrcutreService padss,
            UserService us) {
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

            us.saveRole(new Role("ROLE_USER"));
            us.saveRole(new Role( "ROLE_MANAGER"));
            us.saveRole(new Role( "ROLE_ADMIN"));
            us.saveRole(new Role( "ROLE_SUPER_ADMIN"));

            us.saveUser(new User( "Rachid", "rachid55", "password", List.of()));
            us.saveUser(new User( "abdessamad", "abde53", "password", List.of()));

            us.addRoleToUser("abde53","ROLE_ADMIN");
            us.addRoleToUser("rachid55","ROLE_USER");
        };
    }
}
