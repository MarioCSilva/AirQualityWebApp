package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.AirData;
import tqs.airquality.model.CacheObjDetails;

import tqs.airquality.model.CityAirQuality;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {
    private static final Logger LOG = LogManager.getLogger(CityServiceTest.class);

    private static MockedStatic<ServiceCache> mockCache;

    private static CityAirQuality airQualAveiro;

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private AirQualityService airQualService;

    @BeforeAll
    public static void setUp() {
        mockCache = Mockito.mockStatic(ServiceCache.class);

        AirData[] airDataAveiro = new AirData[1];
        airDataAveiro[0] = new AirData(1.0, 26.0, 0.708736, 0.57969, 296.235, 57.0,
                2.0, 28.0, 1.0, 1.0, 1.0,"Molds");
        airQualAveiro = new CityAirQuality(
                "Aveiro", "Europe/Lisbon","PT", "02", -8.64554, 40.64427,
                airDataAveiro);
    }

    @Test
    public void testGetCityAirQualityByValidName() {
        LOG.info("Testing Getting City Air Quality by Valid Name");

        String requestUrl = "https://api.weatherbit.io/v2.0/current/airquality?city=aveiro&key=c731341737c746a08ca0e6c8fc895da0";
        when(restTemplate.getForObject(
                requestUrl, CityAirQuality.class))
                .thenReturn(airQualAveiro);

        mockCache.when(() -> ServiceCache.checkCache("city=aveiro"))
                .thenReturn(new CacheObjDetails(false, null));

        Optional<CityAirQuality> response = airQualService.getCityAirQualityByName("aveiro", Optional.empty());

        verify(restTemplate, times(1)).getForObject(requestUrl, CityAirQuality.class);

        assertFalse(response.isEmpty());
        assertEquals(response.get().getAirData()[0].getAqi(), airQualAveiro.getAirData()[0].getAqi());
        assertEquals(response.get().getCityName(), airQualAveiro.getCityName());
        assertEquals(response.get().getCountryCode(), airQualAveiro.getCountryCode());
    }


    @Test
    public void testGetCityAirQualityByValidNameAndCountry() {
        LOG.info("Testing Getting City Air Quality by Valid Name and Country");

        String requestUrl = "https://api.weatherbit.io/v2.0/current/airquality?city=aveiro&country=Portugal&key=c731341737c746a08ca0e6c8fc895da0";
        when(restTemplate.getForObject(
                requestUrl, CityAirQuality.class))
                .thenReturn(airQualAveiro);

        mockCache.when(() -> ServiceCache.checkCache("city=aveiro&country=Portugal"))
                .thenReturn(new CacheObjDetails(false, null));

        Optional<CityAirQuality> response = airQualService.getCityAirQualityByName("aveiro", Optional.of("Portugal"));

        verify(restTemplate, times(1)).getForObject(requestUrl, CityAirQuality.class);

        assertFalse(response.isEmpty());
        assertEquals(response.get().getAirData()[0].getAqi(), airQualAveiro.getAirData()[0].getAqi());
        assertEquals(response.get().getCityName(), airQualAveiro.getCityName());
        assertEquals(response.get().getCountryCode(), airQualAveiro.getCountryCode());
    }

    @Test
    public void testGetCityAirQualityByInvalidName() {
        LOG.info("Testing Getting City Air Quality by Invalid Name");

        String requestUrl = "https://api.weatherbit.io/v2.0/current/airquality?city=12321321321&key=c731341737c746a08ca0e6c8fc895da0";
        when(restTemplate.getForObject(
                requestUrl, CityAirQuality.class))
                .thenReturn(null);

        mockCache.when(() -> ServiceCache.checkCache("city=12321321321"))
                .thenReturn(new CacheObjDetails(false, null));

        Optional<CityAirQuality> response = airQualService.getCityAirQualityByName("12321321321", Optional.empty());

        verify(restTemplate, times(1)).getForObject(requestUrl, CityAirQuality.class);

        assertTrue(response.isEmpty());
    }

    @Test
    public void testGetCityAirQualityByValidId() {
        LOG.info("Testing Getting City Air Quality by Valid Id");

        String requestUrl = "https://api.weatherbit.io/v2.0/current/airquality?city_id=2742611&key=c731341737c746a08ca0e6c8fc895da0";
        when(restTemplate.getForObject(
                requestUrl, CityAirQuality.class))
                .thenReturn(airQualAveiro);

        mockCache.when(() -> ServiceCache.checkCache("city_id=2742611"))
                .thenReturn(new CacheObjDetails(false, null));

        Optional<CityAirQuality> response = airQualService.getCityAirQualityById(2742611);

        verify(restTemplate, times(1)).getForObject(requestUrl, CityAirQuality.class);

        assertFalse(response.isEmpty());
        assertEquals(response.get().getAirData()[0].getAqi(), airQualAveiro.getAirData()[0].getAqi());
        assertEquals(response.get().getCityName(), airQualAveiro.getCityName());
        assertEquals(response.get().getCountryCode(), airQualAveiro.getCountryCode());
    }

    @Test
    public void testGetCityAirQualityByInvalidId() {
        LOG.info("Testing Getting City Air Quality by Invalid Id");

        String requestUrl = "https://api.weatherbit.io/v2.0/current/airquality?city_id=9999&key=c731341737c746a08ca0e6c8fc895da0";
        when(restTemplate.getForObject(
                requestUrl, CityAirQuality.class))
                .thenReturn(null);

        mockCache.when(() -> ServiceCache.checkCache("city_id=9999"))
                .thenReturn(new CacheObjDetails(false, null));

        Optional<CityAirQuality> response = airQualService.getCityAirQualityById(9999);

        verify(restTemplate, times(1)).getForObject(requestUrl, CityAirQuality.class);

        assertTrue(response.isEmpty());
    }

    @AfterAll
    public static void tearDown() {
        mockCache.close();
    }
}
