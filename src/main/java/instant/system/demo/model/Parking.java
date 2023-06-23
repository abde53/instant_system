package instant.system.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

// class parking
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table
@ApiModel(description = "model of parking class")
public class Parking {
    @ApiModelProperty(notes = "Id of parking",name="id",required=true,value="1")
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
    @ApiModelProperty(notes = "name of parking",name="name",required=true,value="parking centre ville")
    @NotBlank
    @Column(nullable = false)
    private String name;
    @ApiModelProperty(notes = "capacity of parking",name="capacity",required=true,value="53")
    @Column(nullable = false)
    private String capacity;
    @ApiModelProperty(notes = "le nombre de places disponibles",name="available_place",required=true,value="40")
    @Column(nullable = false)
    private String available_place;
    @ApiModelProperty(notes = "La distance entre le parking et l'emplacement actuel de l'utilisateur qui a envoyé la requête POST.",name="distance",required=true,value="40")
    @Column(nullable = false)
    private String distance;

    @ApiModelProperty(notes = "GPS Coordinate.",name="latitude",required=true,value="40.2545648")
    private String latitude;
    @ApiModelProperty(notes = "GPS Coordinate.",name="longitude",required=true,value="02.2554648")
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
