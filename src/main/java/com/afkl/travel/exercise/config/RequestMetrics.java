package com.afkl.travel.exercise.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static io.micrometer.core.instrument.binder.BaseUnits.MILLISECONDS;

@Component
public class RequestMetrics {

    private final MeterRegistry meterRegistry;
    private final Timer timer;
    private Counter totalRequestsCounter;
    private Counter okResponsesCounter;
    private Counter error4xxCounter;
    private Counter error5xxCounter;
    private Counter requestsAverageTimeCounter;
    private Counter requestsMaxTimeCounter;

    public RequestMetrics(MeterRegistry meterRegistry, Timer timer) {
        this.meterRegistry = meterRegistry;
        this.timer = meterRegistry.timer("travel.locations.timer");
    }

    @PostConstruct
    public void initMetrics() {
        totalRequestsCounter = Counter.builder("travel.locations.timer.total-count")
                .description("Total number of requests processed")
                .baseUnit("NUMBER")
                .register(meterRegistry);

        okResponsesCounter = Counter.builder("travel.locations.timer.2xx-total-count")
                .description("Total number of requests resulted in an OK response")
                .baseUnit("NUMBER")
                .register(meterRegistry);

        error4xxCounter = Counter.builder("travel.locations.timer.4xx-total-count")
                .description("Total number of requests resulted in a 4xx response")
                .baseUnit("NUMBER")
                .register(meterRegistry);

        error5xxCounter = Counter.builder("travel.locations.timer.5xx-total-count")
                .description("Total number of requests resulted in a 5xx response")
                .baseUnit("NUMBER")
                .register(meterRegistry);

        requestsMaxTimeCounter = Counter.builder("travel.locations.timer.max")
                .description("Max response time of all requests")
                .baseUnit(MILLISECONDS)
                .register(meterRegistry);

        requestsAverageTimeCounter = Counter.builder("travel.locations.timer.average")
                .description("Average response time of all requests")
                .baseUnit(MILLISECONDS)
                .register(meterRegistry);
    }

    public void incrementTotalRequestsCounter() {
        totalRequestsCounter.increment();
    }

    public void incrementOkResponses() {
        okResponsesCounter.increment();
        totalRequestsCounter.increment();
    }

    public void incrementError4xx() {
        error4xxCounter.increment();
        totalRequestsCounter.increment();
    }

    public void incrementError5xx() {
        error5xxCounter.increment();
        totalRequestsCounter.increment();
    }

    public void captureTime(long startTime) {
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        timer.record(timeTaken, TimeUnit.MILLISECONDS);
    }

    public void calculateRequestsAverageTimeCounter() {
        requestsAverageTimeCounter.increment(timer.mean(TimeUnit.MILLISECONDS));
    }

    public void calculateRequestsMaxTimeCounter() {
        requestsMaxTimeCounter.increment(timer.max(TimeUnit.MILLISECONDS));
    }
}