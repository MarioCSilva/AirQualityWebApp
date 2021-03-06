package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;

import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.City;
import tqs.airquality.repository.CityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    private static final Logger LOG = LogManager.getLogger(CityServiceTest.class);

    private static MockedStatic<ServiceCache> mockCache;

    private static City aveiro;
    private static City porto;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @BeforeAll
    public static void setUp() {
        aveiro = new City(2742611, "Aveiro", "02",
                "PT", "Portugal", 40.64427, -8.64554);
        porto = new City(2735943, "Porto", "17",
                "PT", "Portugal", 41.14961, -8.61099);

        mockCache = Mockito.mockStatic(ServiceCache.class);
        mockCache.when(() -> ServiceCache.checkCache("cities"))
                .thenReturn(new CacheObjDetails(false, null));
    }

    @Test
    void getAllCitiesTest() {
        LOG.info("Testing Request to City Service to Get All Cities");

        Mockito.when(cityRepository.findAll()).thenReturn(Arrays.asList(aveiro, porto));

        List<City> cities = cityService.getAllCities();

        assertThat(cities).hasSize(2)
                .extracting(City::getCityName).containsOnly(aveiro.getCityName(), porto.getCityName());

        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
    }

    @Test
    void whenGetAllCitiesRepositoryEmpty_thenReadCSVAndReturnCitiesTest() {
        LOG.info("Testing Request to City Service to Get All Cities from CSV");

        Mockito.when(cityRepository.findAll()).thenReturn(new ArrayList<>());

        List<City> cities = cityService.getAllCities();

        assertThat(cities).hasSize(112);

        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
        Mockito.verify(cityRepository, Mockito.times(112)).save(any(City .class));
    }

    @AfterAll
    public static void tearDown() {
        mockCache.close();
    }
}
