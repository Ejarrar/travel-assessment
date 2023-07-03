package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.model.dto.TravelLocationDto;

import java.util.List;

public interface TravelLocationService {

    List<TravelLocationDto> getLocations(String language);

    TravelLocationDto getLocationByTypeAndCode(String type, String code, String language);
}
