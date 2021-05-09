package tqs.airquality.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.City;
import tqs.airquality.service.AirQualityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CityRepositoryTest {
    private static final Logger LOG = LogManager.getLogger(AirQualityService.class);

    private static MockedStatic<ServiceCache> mockCache;

    private static City aveiro;
    private static City porto;

    @Autowired
    private CityRepository repository;

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
    void saveValidCity_thenReturnCitiesTest() {
        LOG.info("Testing City Repository Storing and Returning Correct Cities");

        repository.save(aveiro);
        repository.save(porto);
        List<City> cities = repository.findAll();

        assertThat(cities).hasSize(2)
                .extracting(City::getCityName).containsOnly(aveiro.getCityName(), porto.getCityName());
    }

    @AfterAll
    public static void tearDown() {
        mockCache.close();
    }

}
