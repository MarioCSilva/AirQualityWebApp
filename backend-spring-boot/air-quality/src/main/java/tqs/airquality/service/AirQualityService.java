package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.cache.AirQualityCache;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.CityAirQuality;

import java.util.Optional;

@Service
public class AirQualityService {
    private static final Logger LOG = LogManager.getLogger(AirQualityService.class);

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/current/airquality?";
    private static final String KEY = "&key=c731341737c746a08ca0e6c8fc895da0";
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public Optional<CityAirQuality> getCityAirQuality(String city, Optional<String> country) {
        String request = "";
        if (country.isEmpty())
            request = "city=" + city;
        else
            request = "city=" + city + "&country=" + country;

        CacheObjDetails cacheObjDetails = AirQualityCache.checkCache(request);
        CityAirQuality cityAirQuality = (CityAirQuality) cacheObjDetails.getReturnValue();

        if (!cacheObjDetails.getFound()) {
            String url = BASE_URL;
            try {
                if (!country.isEmpty()) {
                    url += "city=" + city + "&country=" + country.get() + KEY;
                } else {
                    url += "city=" + city + KEY;
                }
                LOG.info("Request Made to External API With Url: " + url);
                cityAirQuality = this.restTemplate.getForObject(url, CityAirQuality.class);
            } catch (Exception ex) {
                LOG.error("Error When Making a Request to External API");
            }
        }

        AirQualityCache.cacheRequest("city=" + city + "&country=" + country, cityAirQuality);

        return Optional.ofNullable(cityAirQuality);
    }
}
