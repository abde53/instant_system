package instant.system.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import instant.system.demo.model.Parking;
import instant.system.demo.model.ParkingApi;
import instant.system.demo.model.ParkingApiDataStructure;
import instant.system.demo.repository.ParkingApiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@AllArgsConstructor
@Service
public class ParkingApiService {

    private final ParkingApiRepository parkingApiRepository;

    public List<ParkingApi> getAllParkingsApi() {
        return parkingApiRepository.findAll();
    }

    public void addParkingApi(ParkingApi p) {
        parkingApiRepository.save(p);
    }



    // method qui fait le travail de mapping entre Json et l'objet Parking.
    // Elle retourne que les parking dont la distance < proximity
    public List<Parking> getNearbyParkings(double latitude, double longitude, int proximity, String city) {
        ParkingApi pa = parkingApiRepository.findByCity(city);
        List<Parking> lp = new ArrayList<>();


        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(pa.getUrl(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonString);
            ArrayNode records = (ArrayNode) root.get(pa.getRootNode());

            List<Parking> parkingList = new ArrayList<>();
            for (JsonNode record : records) {
                Class c;
                Object obj = null;
                c = Class.forName("instant.system.demo.model.Parking");
                obj = c.newInstance();
                for(ParkingApiDataStructure pads : parkingApiRepository.findAllById(pa.getId()))
                {

                    try {
                        Class<?> clazz = obj.getClass();
                        Field field = clazz.getDeclaredField(pads.getObjectMemberName());
                        field.setAccessible(true);
                        field.set(obj, record.at(pads.getApiMemberName()).asText());


                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                }
                Parking p = (Parking) obj;
                if(!p.getLatitude().isEmpty() || !p.getLongitude().isEmpty())
                {
                    p.setDistance(latitude, longitude);
                    if(Integer.valueOf(p.getDistance()) <= proximity)
                        parkingList.add((Parking) obj);
                }



            }

            return parkingList;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
