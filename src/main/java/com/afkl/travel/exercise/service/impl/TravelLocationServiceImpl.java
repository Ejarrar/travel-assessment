package com.afkl.travel.exercise.service.impl;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.dto.TravelLocationDto;
import com.afkl.travel.exercise.model.entity.LocationEntity;
import com.afkl.travel.exercise.repository.TravelLocationRepository;
import com.afkl.travel.exercise.service.TravelLocationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelLocationServiceImpl implements TravelLocationService {

    private final TravelLocationRepository repository;

    private final ModelMapper modelMapper;

    public TravelLocationServiceImpl(TravelLocationRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TravelLocationDto> getLocations(String lang) {
        List<LocationEntity> locationEntities = repository.findAllByTranslationLanguage(lang);
        return locationEntities.stream().map(this::mapToTravelLocation).collect(Collectors.toList());
    }

    @Override
    public TravelLocationDto getLocationByTypeAndCode(String type, String code, String lang) {
        LocationEntity locationEntity = repository.findByTypeAndCodeAndTranslationLanguage(type, code, lang).orElseThrow(LocationNotFoundException::new);
        return mapToTravelLocation(locationEntity);
    }

    private TravelLocationDto mapToTravelLocation(LocationEntity location) {
        TravelLocationDto travelLocationDto = modelMapper.map(location, TravelLocationDto.class);
        travelLocationDto.setDescription(location.getTranslation().getDescription());
        return travelLocationDto;
    }
}
