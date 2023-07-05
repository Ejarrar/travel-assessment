package com.afkl.travel.exercise.api.v1;

import com.afkl.travel.exercise.config.RequestMetrics;
import com.afkl.travel.exercise.model.dto.TravelLocationDto;
import com.afkl.travel.exercise.service.TravelLocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TravelLocationController.class)
public class TravelLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TravelLocationService travelLocationService;
    @MockBean
    private RequestMetrics requestMetrics;

    @Test
    public void testGetAllLocations() throws Exception {

        List<TravelLocationDto> locations = Arrays.asList(
                new TravelLocationDto("code1", "location1", "type1", 123, 456, "location description1", null, null),
                new TravelLocationDto("code2", "location2", "type2", 789, 12, "location description2", null, null)
        );

        Mockito.when(travelLocationService.getLocations(anyString())).thenReturn(locations);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/locations").with(httpBasic("someuser", "psw"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.travelLocations[0].code").value("code1"))
                .andExpect(jsonPath("$.travelLocations[0].name").value("location1"))
                .andExpect(jsonPath("$.travelLocations[0].type").value("type1"))
                .andExpect(jsonPath("$.travelLocations[0].latitude").value(123))
                .andExpect(jsonPath("$.travelLocations[0].longitude").value(456))
                .andExpect(jsonPath("$.travelLocations[1].code").value("code2"))
                .andExpect(jsonPath("$.travelLocations[1].name").value("location2"))
                .andExpect(jsonPath("$.travelLocations[1].type").value("type2"))
                .andExpect(jsonPath("$.travelLocations[1].latitude").value(789))
                .andExpect(jsonPath("$.travelLocations[1].longitude").value(12));
    }

    @Test
    public void testGetAllLocationsWhenAnonymousUserShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/locations").with(anonymous())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testGetLocationByTypeAndCode() throws Exception {
        TravelLocationDto locationDto = new TravelLocationDto("code1", "location1", "type1", 123, 456,"location description1", null, null);

        Mockito.when(travelLocationService.getLocationByTypeAndCode(anyString(), anyString(), anyString())).thenReturn(locationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/locations/type1/code1").with(httpBasic("someuser", "psw"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("code1"))
                .andExpect(jsonPath("$.name").value("location1"))
                .andExpect(jsonPath("$.type").value("type1"))
                .andExpect(jsonPath("$.latitude").value(123))
                .andExpect(jsonPath("$.longitude").value(456));
    }

    @Test
    public void testGetLocationByTypeAndCodeWhenAnonymousUserShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/locations/type1/code1").with(anonymous())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
