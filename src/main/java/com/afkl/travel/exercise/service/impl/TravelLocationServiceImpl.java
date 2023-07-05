package com.afkl.travel.exercise.service.impl;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.dto.TravelLocationDto;
import com.afkl.travel.exercise.model.entity.LocationEntity;
import com.afkl.travel.exercise.model.entity.TranslationEntity;
import com.afkl.travel.exercise.repository.TravelLocationRepository;
import com.afkl.travel.exercise.service.TravelLocationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        List<LocationEntity> locationEntities = repository.findAll();
        return locationEntities.stream().map(location -> mapToTravelLocation(location, lang)).collect(Collectors.toList());
    }

    @Override
    public TravelLocationDto getLocationByTypeAndCode(String type, String code, String lang) {
        LocationEntity locationEntity = repository.findByTypeAndCode(type, code).orElseThrow(LocationNotFoundException::new);
        return mapToTravelLocation(locationEntity, lang);
    }


    private TravelLocationDto mapToTravelLocation(LocationEntity location, String lang) {
        Optional<TranslationEntity> translationEntity = location.getTranslation().stream().filter(t -> lang.equalsIgnoreCase(t.getLanguage())).findFirst();

        TravelLocationDto travelLocationDto = modelMapper.map(location, TravelLocationDto.class);

        if (translationEntity.isPresent()) {
            TranslationEntity translation = translationEntity.get();
            travelLocationDto.setDescription(translation.getDescription());
            travelLocationDto.setName(translation.getName());
        }

        return travelLocationDto;
    }
}
