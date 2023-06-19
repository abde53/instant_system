package instant.system.demo;

import instant.system.demo.model.ParkingApi;
import instant.system.demo.model.ParkingApiDataStructure;
import instant.system.demo.service.ParkingApiDataStrcutreService;
import instant.system.demo.service.ParkingApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
