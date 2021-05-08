package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.model.City;
import tqs.airquality.repository.CityRepository;

import java.io.IOException;


@ExtendWith(MockitoExtension.class)
public class AirQualityServiceTest {
    private static final Logger LOG = LogManager.getLogger(CityServiceTest.class);

    private static City aveiro;
    private static City porto;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    public void setup() {
        aveiro = new City(2742611, "Aveiro", "02",
                "PT", "Portugal", 40.64427, -8.64554);
        porto = new City(2735943, "Porto", "17",
                "PT", "Portugal", 41.14961, -8.61099);

        //given(cityRepository.findAll()).willReturn(Arrays.asList(aveiro, porto));
    }

    @Test
    void getAllCitiesTest() throws IOException {
        LOG.info("");
    }

}
