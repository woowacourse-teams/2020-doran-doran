package com.grasshouse.dorandoran.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class WebRestControllerTest {

    @Test
    public void checkProfile() {
        String port = "8080";
        MockEnvironment env = new MockEnvironment();
        env.setProperty("local.server.port", port);

        WebRestController controller = new WebRestController(env);

        assertThat(controller.getPort()).isEqualTo(port);
    }
}