package com.example.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Configuration class for Gson.
 */
@Configuration
public class GsonConfig {

    /**
     * Creates and configures a Gson instance with pretty printing enabled.
     *
     * @return The configured Gson instance.
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
