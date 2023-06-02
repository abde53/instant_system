package instant.system.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// class parking
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table
public class Parking {
    @Id
    @SequenceGenerator(
            name = "parking_sequence",
            sequenceName = "parking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "parking_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String capacity;
    @Column(nullable = false)
    private String available_place;
    @Column(nullable = false)
    private String distance;
    private String latitude;
    private String longitude;


    public Parking(String n, String c, String a, String d, String lat, String longi) {
        this.name = n;
        this.available_place = a;
        this.capacity = c;
        this.distance = d;
        this.latitude = lat;
        this.longitude = longi;
    }

    // methode qui permet de calculer la distance entre le parking courant et la position de l'utilisateur
    public void setDistance(double targetLatitude, double targetLongitude)
    {
        if(!latitude.isEmpty() && !longitude.isEmpty())
        {
            double currentLatitude = Double.parseDouble(latitude);
            double currentLongitude = Double.parseDouble(longitude);

            final int earthRadiusM = 6371000;

            double dLat = Math.toRadians(targetLatitude - currentLatitude);
            double dLon = Math.toRadians(targetLongitude - currentLongitude);

            double lat1 = Math.toRadians(currentLatitude);
            double lat2 = Math.toRadians(targetLatitude);

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadiusM * c;

            this.distance = String.format(String.valueOf((int) distance));
        }
        else
            this.distance = "0";
    }
}
