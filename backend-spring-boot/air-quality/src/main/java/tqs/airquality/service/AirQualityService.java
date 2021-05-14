package tqs.airquality.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.CityAirQuality;

import java.util.Optional;

@Service
public class AirQualityService {
    private static final Logger LOG = LogManager.getLogger(AirQualityService.class);

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/current/airquality?";
    private static final String KEY = "&key=c731341737c746a08ca0e6c8fc895da0";

    @Autowired
    private RestTemplate restTemplate;

    public Optional<CityAirQuality> getCityAirQualityByName(String city, Optional<String> country) {
        String request = "";
        if (country.isEmpty())
            request = String.format("city=%s", city);
        else
            request = String.format("city=%s&country=%s", city, country.get());

        return getCityAirQuality(request);
    }

    public Optional<CityAirQuality> getCityAirQualityById(int cityId) {
        String request = String.format("city_id=%s", cityId);

        return getCityAirQuality(request);
    }

    private Optional<CityAirQuality> getCityAirQuality(String request) {
        CacheObjDetails cacheObjDetails = ServiceCache.checkCache(request);
        CityAirQuality cityAirQuality = null;

        if (Boolean.FALSE.equals(cacheObjDetails.getFound())) {
            try {
                String url = String.format("%s%s%s", BASE_URL, request, KEY);
                LOG.info(String.format("Request Made to External API With Url: %s", url));
                cityAirQuality = this.restTemplate.getForObject(url, CityAirQuality.class);
            } catch (Exception ex) {
                LOG.error("Error When Making a Request to External API");
            }
        } else {
            cityAirQuality = (CityAirQuality) cacheObjDetails.getReturnValue();
        }

        ServiceCache.cacheRequest(request, cityAirQuality);

        return Optional.ofNullable(cityAirQuality);
    }
}
