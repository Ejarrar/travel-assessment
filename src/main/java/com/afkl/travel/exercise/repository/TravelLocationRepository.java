package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelLocationRepository extends JpaRepository<LocationEntity,Long> {

    Optional<LocationEntity> findByTypeAndCodeAndTranslationLanguage(String type, String code,String language);

    List<LocationEntity> findAllByTranslationLanguage(String lang);
}
