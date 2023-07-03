package com.afkl.travel.exercise.config;

import com.afkl.travel.exercise.api.v1.interceptor.TravelLocationsMetricInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final TravelLocationsMetricInterceptor travelLocationsMetricInterceptor;

    public InterceptorConfig(TravelLocationsMetricInterceptor travelLocationsMetricInterceptor) {
        this.travelLocationsMetricInterceptor = travelLocationsMetricInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(travelLocationsMetricInterceptor);
    }
}