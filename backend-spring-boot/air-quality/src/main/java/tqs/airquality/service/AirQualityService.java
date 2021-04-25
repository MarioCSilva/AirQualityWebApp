package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.model.CityAirQuality;

import java.util.Optional;

@Service
public class AirQualityService {
    private static final Logger LOG = LogManager.getLogger(AirQualityService.class);

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/current/airquality?";
    private static final String KEY = "&key=c731341737c746a08ca0e6c8fc895da0";
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public Optional<CityAirQuality> getCityAirQuality(String city, Optional<String> country) {
        CityAirQuality result = null;
        String url = BASE_URL;
        try {
            if (!country.isEmpty()) {
                url += "city=" + city + "&country=" + country.get() + KEY;
            } else {
                url += "city=" + city + KEY;
            }
            LOG.info("Request Made to External API With Url: " + url);
            result = this.restTemplate.getForObject(url, CityAirQuality.class);
        }
        catch (Exception ex) {
            LOG.error("Error When Making a Request to External API");
        }
        return Optional.ofNullable(result);
    }
}
