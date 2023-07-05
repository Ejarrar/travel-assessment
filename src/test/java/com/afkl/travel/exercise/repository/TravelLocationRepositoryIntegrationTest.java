package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.entity.LocationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
public class TravelLocationRepositoryIntegrationTest {

    @Autowired
    private TravelLocationRepository travelLocationRepository;

    @Test
    public void testLocationInitializationData() {
        List<LocationEntity> result = travelLocationRepository.findAll();

        assertFalse(result.isEmpty());
    }
    @Test
    public void testFindByTypeAndCode() {

        Optional<LocationEntity> result = travelLocationRepository.findByTypeAndCode("country", "US");

        assertTrue(result.isPresent());

        assertEquals("US", result.get().getCode());
        assertEquals("country", result.get().getType());
    }

    @Test
    public void testFindByTypeAndCode_NotFound() {
        Optional<LocationEntity> result = travelLocationRepository.findByTypeAndCode("type2", "code2");

        assertTrue(result.isEmpty());
    }
}
