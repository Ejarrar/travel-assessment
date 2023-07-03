package com.afkl.travel.exercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TravelLocationListDto {

    private List<TravelLocationDto> travelLocations;

}
