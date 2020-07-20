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

    public Double calculateDistance(Location location) {
        Double targetLatitude = location.getLatitude();
        Double targetLongitude = location.getLongitude();

        Double latitudeDistance = toRadian(targetLatitude - this.latitude);
        Double longitudeDistance = toRadian(targetLongitude - this.longitude);
        Double arcSin = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2) +
            Math.cos(toRadian(this.latitude)) * Math.cos(toRadian(targetLatitude)) *
                Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        Double angularDistance = 2 * Math.atan2(Math.sqrt(arcSin), Math.sqrt(1 - arcSin));

        return EARTH_RADIUS * angularDistance;
    }

    private Double toRadian(Double value) {
        return value * Math.PI / 180;
    }
}
