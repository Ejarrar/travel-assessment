package com.afkl.travel.exercise.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Timer timer(MeterRegistry meterRegistry) {
        return Timer.builder("travelLocationsTimer")
                .description("Travel Locations APIs Timer")
                .register(meterRegistry);
    }
}