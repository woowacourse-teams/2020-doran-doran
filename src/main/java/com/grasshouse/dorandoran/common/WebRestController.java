package com.grasshouse.dorandoran.common;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WebRestController {

    private final Environment env;

    @GetMapping("/profile")
    public String getProfile() {
        return env.getProperty("local.server.port");
    }
}
