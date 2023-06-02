package instant.system.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class ParkingApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @Column(unique = true,
            nullable = false)
    private String city;
    private String rootNode;

    public ParkingApi(String url, String city, String rootNode) {
        this.url = url;
        this.city = city;
        this.rootNode = rootNode;
    }



}
