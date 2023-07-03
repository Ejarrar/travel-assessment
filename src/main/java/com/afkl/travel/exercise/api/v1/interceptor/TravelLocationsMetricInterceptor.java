package com.afkl.travel.exercise.api.v1.interceptor;

import com.afkl.travel.exercise.config.RequestMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TravelLocationsMetricInterceptor implements HandlerInterceptor {

    private final RequestMetrics requestMetrics;

    @Autowired
    public TravelLocationsMetricInterceptor(RequestMetrics requestMetrics) {
        this.requestMetrics = requestMetrics;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        calculateAndPublishStatistics(request, response);
    }

    private void calculateAndPublishStatistics(HttpServletRequest request, HttpServletResponse response) {
        requestMetrics.captureTime((long) request.getAttribute("startTime"));
        requestMetrics.calculateRequestsAverageTimeCounter();
        requestMetrics.calculateRequestsMaxTimeCounter();

        switch (response.getStatus() / 100) {
            case 2:
                requestMetrics.incrementOkResponses();
                break;
            case 4:
                requestMetrics.incrementError4xx();
                break;
            case 5:
                requestMetrics.incrementError5xx();
                break;
            default:
                requestMetrics.incrementTotalRequestsCounter();
                break;
        }
    }
}