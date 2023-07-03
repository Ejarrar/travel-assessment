package com.afkl.travel.exercise.api.v1;

import com.afkl.travel.exercise.model.dto.TravelLocationDto;
import com.afkl.travel.exercise.model.dto.TravelLocationListDto;
import com.afkl.travel.exercise.model.enums.LocationType;
import com.afkl.travel.exercise.service.TravelLocationService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/locations")
@Tag(name = "Travel Locations REST APIs v1")
public class TravelLocationController {
    private final TravelLocationService service;

    public TravelLocationController(TravelLocationService service) {
        this.service = service;
    }

    @GetMapping
    @Timed(value = "get.locations.timer", description = "Time taken to process get all locations API endpoint", histogram = true)
    public TravelLocationListDto getAllLocations(
            @RequestHeader(name = "accept-language") String lang
    ) {
        return new TravelLocationListDto(service.getLocations(lang == null ? "EN" : lang));
    }

    @GetMapping("/{type}/{code}")
    @Timed(value = "get.locations.by.type.code.timer", description = "Time taken to process get location by type and code API endpoint", histogram = true)
    public TravelLocationDto getLocationByTypeAndCode(
            @RequestHeader(name = "accept-language") String lang,
            @PathVariable LocationType type,
            @PathVariable String code
    ) {
        return service.getLocationByTypeAndCode(type.name(), code, lang == null ? "EN" : lang);
    }
}