package tqs.airquality.integration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirQualityControllerTestIT {
    private static final Logger LOG = LogManager.getLogger(AirQualityControllerTestIT.class);

    @LocalServerPort
    private int port;

    private static String baseUrl;

    @BeforeAll
    public static void init(){
        baseUrl = "http://127.0.0.1:";
    }

    @Test
    @Order(1)
    void whenGetCacheInitially_thenReturnZeros() {
        LOG.info("Checking Inital Cache Values");

        given().when().get(baseUrl+port+"/api/v1/cachestats").then()
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .and().body("hits", is(0))
                .and().body("misses", is(0))
                .and().body("totalChecks", is(0));
    }

    @Test
    @Order(2)
    void whenGetFirstAirQualityByCityName_thenReturnFirstAirQuality() {
        LOG.info("First Request to API to Get Air Quality");

        given().when().get(baseUrl+port+"/api/v1/airquality?city=aveiro").then()
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .and().body("city_name", equalTo("Aveiro"))
                .and().body("country_code", equalTo("PT"))
                .and().body("lon", equalTo(-8.64554f))
                .and().body("lat", equalTo(40.64427f))
                .and().body("timezone", equalTo("Europe/Lisbon"))
                .and().body("state_code", equalTo("02"))
                .and().body("data", hasSize(1))
                .and().body("data[0].size()", is(12))
                .and().body("data[0]", hasKey("mold_level"))
                .and().body("data[0]", hasKey("aqi"))
                .and().body("data[0]", hasKey("pm10"))
                .and().body("data[0]", hasKey("co"))
                .and().body("data[0]", hasKey("o3"))
                .and().body("data[0]", hasKey("predominant_pollen_type"))
                .and().body("data[0]", hasKey("pollen_level_tree"))
                .and().body("data[0]", hasKey("pollen_level_weed"))
                .and().body("data[0]", hasKey("no2"))
                .and().body("data[0]", hasKey("pm25"))
                .and().body("data[0]", hasKey("pollen_level_grass"));
    }

    @Test
    @Order(3)
    void whenGetFirstAirQuality_thenReturn1MissAnd1Total() {
        LOG.info("Check Cache after First Request to API");

        get(baseUrl+port+"/api/v1/cachestats").then().assertThat()
                .statusCode(200).contentType(JSON)
                .and().body("hits", is(0))
                .and().body("misses", is(1))
                .and().body("totalChecks", is(1));
    }

    @Test
    @Order(4)
    void whenGetSecondAirQualityTwice_thenReturnCachedAirQuality() {
        LOG.info("Making another Request to API");

        get(baseUrl + port + "/api/v1/airquality?city=aveiro");

        LOG.info("Checking Cache After Two Equal Requests to API");

        given().when().get(baseUrl+port+"/api/v1/cachestats").then().assertThat()
                .statusCode(200).contentType(JSON)
                .and().body("hits", is(1))
                .and().body("misses", is(1))
                .and().body("totalChecks", is(2));
    }

    @Test
    void whenGetValidAirQualityByCityNameAndCountry_thenReturnValidAirQuality() {
        LOG.info("Making Request to API to Get Air Quality By City Name and Country");

        given().when().get(baseUrl+port+"/api/v1/airquality?city=aveiro&country=Portugal").then()
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .and().body("city_name", equalTo("Aveiro"));
    }

    @Test
    void whenGetInvalidAirQualityByCityName_thenReturnNotFound() {
        LOG.info("Making Request to API to Get Air Quality with Invalid City Name");

        given().when().get(baseUrl+port+"/api/v1/airquality?city=12121").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void whenGetInvalidAirQualityByCityNameAndCountry_thenReturnNotFound() {
        LOG.info("Making Request to API to Get Air Quality with Invalid City Name and Country");

        given().when().get(baseUrl+port+"/api/v1/airquality?city=12121").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void whenGetValidAirQualityById_thenValidAirQuality() {
        LOG.info("Making Request to API to Get Air Quality by City Id");

        given().when().get(baseUrl+port+"/api/v1/airquality/2742611").then()
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .and().body("city_name", equalTo("Aveiro"));
    }

    @Test
    void whenGetInvalidAirQualityById_thenReturnNotFound() {
        LOG.info("Making Request to API to Get Air Quality with Invalid City Id");

        given().when().get(baseUrl+port+"/api/v1/airquality/-99").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void whenGetCities_thenReturnAllCities() {
        LOG.info("Making Request to API to Get List of Cities");

        given().when().get(baseUrl+port+"/api/v1/cities").then()
                .assertThat()
                .statusCode(200)
                .and().body("$.size()", is(112));
    }
}

