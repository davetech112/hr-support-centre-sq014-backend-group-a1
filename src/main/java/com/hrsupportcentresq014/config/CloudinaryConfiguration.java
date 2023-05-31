package com.hrsupportcentresq014.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {
    private final String CLOUD_NAME = "dywhpampa";
    private final String API_KEY = "328139327964175";
    private final String API_SECRET = "-T2CEOL5UgkB8r5-bQzn0aP4hdM";

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);

    }
}
