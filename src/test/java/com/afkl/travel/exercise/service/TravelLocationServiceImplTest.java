package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.dto.TravelLocationDto;
import com.afkl.travel.exercise.model.entity.LocationEntity;
import com.afkl.travel.exercise.model.entity.TranslationEntity;
import com.afkl.travel.exercise.repository.TravelLocationRepository;
import com.afkl.travel.exercise.service.impl.TravelLocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelLocationServiceImplTest {

    @InjectMocks
    private TravelLocationServiceImpl travelLocationService;
    @Mock
    private TravelLocationRepository mockRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @Test
    public void testGetLocations() {
        List<LocationEntity> locationEntities = Arrays.asList(
                createLocationEntity("code1", "type1"),
                createLocationEntity("code2", "type2")
        );
        when(mockRepository.findAll()).thenReturn(locationEntities);

        TravelLocationDto mockDto = new TravelLocationDto();
        when(mockModelMapper.map(any(LocationEntity.class), eq(TravelLocationDto.class))).thenReturn(mockDto);

        List<TravelLocationDto> result = travelLocationService.getLocations("EN");

        verify(mockRepository, times(1)).findAll();

        verify(mockModelMapper, times(locationEntities.size())).map(any(LocationEntity.class), eq(TravelLocationDto.class));

        assertEquals(locationEntities.size(), result.size());
        assertSame(mockDto, result.get(0));
        assertSame(mockDto, result.get(1));
    }

    @Test
    public void testGetLocationsWhenDBIsEmptyThenReturnEmptyList() {
        when(mockRepository.findAll()).thenReturn(emptyList());

        List<TravelLocationDto> result = travelLocationService.getLocations("EN");

        verify(mockRepository, times(1)).findAll();

        verify(mockModelMapper, never()).map(any(LocationEntity.class), eq(TravelLocationDto.class));

        assertEquals(0, result.size());
    }

    @Test
    public void testGetLocationByTypeAndCode() {
        LocationEntity locationEntity = createLocationEntity("code1", "type1");
        when(mockRepository.findByTypeAndCode(eq("type1"), eq("code1"))).thenReturn(Optional.of(locationEntity));

        TravelLocationDto mockDto = new TravelLocationDto();
        when(mockModelMapper.map(eq(locationEntity), eq(TravelLocationDto.class))).thenReturn(mockDto);

        TravelLocationDto result = travelLocationService.getLocationByTypeAndCode("type1", "code1", "EN");

        verify(mockRepository, times(1)).findByTypeAndCode(eq("type1"), eq("code1"));

        verify(mockModelMapper, times(1)).map(eq(locationEntity), eq(TravelLocationDto.class));

        assertSame(mockDto, result);
    }

    @Test
    public void testGetLocationByTypeAndCodeWhenNotFoundShouldThrowException() {
        LocationEntity locationEntity = createLocationEntity("code1", "type1");
        when(mockRepository.findByTypeAndCode(eq("type1"), eq("code1"))).thenReturn(Optional.empty());

        assertThrows( LocationNotFoundException.class, () ->travelLocationService.getLocationByTypeAndCode("type1", "code1", "EN"));

        verify(mockRepository, times(1)).findByTypeAndCode(eq("type1"), eq("code1"));

        verify(mockModelMapper, never()).map(eq(locationEntity), eq(TravelLocationDto.class));
    }

    private LocationEntity createLocationEntity(String code, String type) {
        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setName("translationName");
        translationEntity.setDescription("translationDescription");

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCode(code);
        locationEntity.setType(type);
        locationEntity.setTranslation(Set.of(translationEntity));
        return locationEntity;
    }
}
