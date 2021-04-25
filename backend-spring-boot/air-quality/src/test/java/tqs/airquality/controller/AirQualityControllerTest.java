package tqs.airquality.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.airquality.model.AirData;
import tqs.airquality.model.CityAirQuality;
import tqs.airquality.service.AirQualityService;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

@WebMvcTest(AirQualityController.class)
public class AirQualityControllerTest {
    private static final Logger LOG = LogManager.getLogger(AirQualityControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirQualityService service;

    @Test
    public void testGetAirQualityByValidCity_thenReturnValidAirData() throws Exception {
        CityAirQuality airQualityAveiro = createCityAirQualityObj();

        given(service.getCityAirQuality("Aveiro", Optional.empty()))
                .willReturn(Optional.of(airQualityAveiro));

        mockMvc.perform(get("/api/v1/airquality?city=Aveiro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"))
                .andExpect(jsonPath("lon").value("-8.64554"))
                .andExpect(jsonPath("timezone").value("Europe/Lisbon"))
                .andExpect(jsonPath("lat").value("40.64427"))
                .andExpect(jsonPath("state_code").value("02"))
                .andExpect(jsonPath("country_code").value("PT"));

        verify(service, times(1)).getCityAirQuality("Aveiro", Optional.empty());
    }


    @Test
    public void testGetAirQualityByValidCityAndCountry_thenReturnValidAirData() throws Exception {
        CityAirQuality airQualityAveiro = createCityAirQualityObj();

        given(service.getCityAirQuality("Aveiro", Optional.of("Portugal")))
                .willReturn(Optional.of(airQualityAveiro));

        mockMvc.perform(get("/api/v1/airquality?city=Aveiro&country=Portugal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"))
                .andExpect(jsonPath("lon").value("-8.64554"))
                .andExpect(jsonPath("timezone").value("Europe/Lisbon"))
                .andExpect(jsonPath("lat").value("40.64427"))
                .andExpect(jsonPath("state_code").value("02"))
                .andExpect(jsonPath("country_code").value("PT"));

        verify(service, times(1)).getCityAirQuality("Aveiro", Optional.of("Portugal"));
    }

    @Test
    public void testGetAirQualityByInvalidCity_thenReturnNotFound() throws Exception {
        given(service.getCityAirQuality("xptoAveiro", Optional.empty()))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/airquality?city=xptoAveiro"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCityAirQuality("xptoAveiro", Optional.empty());
    }

    public CityAirQuality createCityAirQualityObj() {
        CityAirQuality cityAir = new CityAirQuality();
        cityAir.setCityName("Aveiro");
        cityAir.setCountryCode("PT");
        cityAir.setLat(40.64427);
        cityAir.setLon(-8.64554);
        cityAir.setTimezone("Europe/Lisbon");
        cityAir.setStateCode("02");

        AirData airData = new AirData();
        airData.setPm10(1.16202);
        airData.setAqi(78);
        airData.setCo(325.024);
        airData.setMoldLevel(1);
        airData.setNo2(1.25845);
        airData.setO3(142.515);
        airData.setPm25(0.391599);
        airData.setPollenLevelGrass(1);
        airData.setPollenLevelTree(1);
        airData.setPollenLevelWeed(1);
        airData.setSo2(0.867061);
        airData.setPredominantPollenType("Molds");

        AirData[] airDataArray = new AirData[1];
        airDataArray[0] = airData;

        cityAir.setAirData(airDataArray);

        return cityAir;
    }


}
