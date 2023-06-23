package instant.system.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table
@ApiModel(description = "Table that contains all mapping between Parking class and return api json")
public class ParkingApiDataStructure {
    @Id
    @GeneratedValue(
            generator = "parking_api_data_structure_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    private String ApiMemberName;
    private String ObjectMemberName;
    @ManyToOne
    private ParkingApi parkingApi;

    public ParkingApiDataStructure(String a, String b, ParkingApi pa)
    {
        this.ApiMemberName = a;
        this.ObjectMemberName = b;
        this.parkingApi = pa;
    }


}
