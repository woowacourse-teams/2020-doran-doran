package com.grasshouse.dorandoran.post.domain;

import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationTest {

    @DisplayName("위도, 경도를 기반으로 두 점 사이의 거리를 구한다.")
    @Test
    void calculateDistance() {
        Location sourceLocation = JAMSIL_STATION;
        Location targetLocation = GANGNAM_STATION;
        Double distance = sourceLocation.calculateDistance(targetLocation);

        assertThat(distance).isCloseTo(6.6, Percentage.withPercentage(5));
    }
}