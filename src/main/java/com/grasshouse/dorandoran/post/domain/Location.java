package com.grasshouse.dorandoran.post.domain;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    private static final int EARTH_RADIUS = 6371;
    private Double longitude;
    private Double latitude;

    public Double calculateDistance(Location targetLocation) {
        double targetLongitude = targetLocation.getLongitude();
        double targetLatitude = targetLocation.getLatitude();
        double longitudeDistance = Math.toRadians(targetLongitude) - Math.toRadians(longitude);
        double latitudeDistance = Math.toRadians(targetLatitude) - Math.toRadians(latitude);

        double arcSin = Math.pow(Math.sin(latitudeDistance / 2), 2)
            + Math.cos(latitude) * Math.cos(targetLatitude)
            * Math.pow(Math.sin(longitudeDistance / 2), 2);

        double distance = 2 * Math.asin(Math.sqrt(arcSin));
        return distance * EARTH_RADIUS;
    }

}
