package com.grasshouse.dorandoran.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class WebRestControllerTest {

    @Test
    public void checkProfile() {
        String expectedProfile = "set1";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);

        WebRestController controller = new WebRestController(env);

        String profile = controller.getProfile();

        assertThat(profile).isEqualTo(expectedProfile);
    }
}