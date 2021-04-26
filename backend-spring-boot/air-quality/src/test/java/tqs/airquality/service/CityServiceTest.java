package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockBean;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import tqs.airquality.model.City;
import tqs.airquality.repository.CityRepository;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CityServiceTest.class)
public class CityServiceTest {
    private static final Logger LOG = LogManager.getLogger(CityServiceTest.class);

    private static City aveiro;
    private static City porto;

    @MockBean
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @BeforeAll
    public static void init() {
        aveiro = new City(2742611, "Aveiro", "02", "PT", "Portugal", 40.64427, -8.64554);
        porto = new City(2735943, "Porto", "17", "PT", "Portugal", 41.14961, -8.61099);
    }

    @Test
    public void getAllCitiesTest() throws IOException {
        LOG.info("Testing Request to City Service to Get All Cities");

        Mockito.when(cityRepository.findAll()).thenReturn(Arrays.asList(aveiro, porto));

        assertEquals(Arrays.asList(aveiro, porto), cityService.getAllCities());
        assertEquals(2, cityService.getAllCities().size());

        Mockito.verify(cityRepository, Mockito.times(2)).findAll();
    }

}
