package tqs.airquality.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.CacheStats;
import tqs.airquality.model.City;
import tqs.airquality.model.CityAirQuality;
import tqs.airquality.service.AirQualityService;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tqs.airquality.service.CityService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class AirQualityController {

    private static final Logger LOG = LogManager.getLogger(AirQualityController.class);

    @Autowired
    AirQualityService service;

    @Autowired
    CityService cityService;

    @GetMapping("/airquality")
    public ResponseEntity<CityAirQuality> getAirQuality(
            @RequestParam(value = "city") String city,
            @RequestParam(value = "country", required = false) String country) {
        LOG.info(String.format("Received Request for /airquality with params: city: %s and country: %s" , city, country));

        Optional<CityAirQuality> optCityAirQuality = service.getCityAirQualityByName(city, Optional.ofNullable(country));
        if (optCityAirQuality.isEmpty()) {
            LOG.warn("Returning City Not Found");
            ServiceCache.cacheRequest(String.format("city=%s&country=%s", city, country), null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CityAirQuality cityAirQuality = optCityAirQuality.get();

        LOG.info(String.format("Returning Air Quality for City: %s of the Country: %s",
                cityAirQuality.getCityName(), cityAirQuality.getCountryCode()));

        return new ResponseEntity<>(cityAirQuality, HttpStatus.OK);
    }


    @GetMapping("/airquality/{id}")
    public ResponseEntity<CityAirQuality> getAirQuality(
            @PathVariable(value = "id") int cityId) {
        LOG.info(String.format("Received Request for /airquality/%s", cityId));

        Optional<CityAirQuality> optCityAirQuality = service.getCityAirQualityById(cityId);
        if (optCityAirQuality.isEmpty()) {
            LOG.warn("Returning City Not Found");
            ServiceCache.cacheRequest(String.format("cityId=%d", cityId), null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CityAirQuality cityAirQuality = optCityAirQuality.get();

        LOG.info(String.format("Returning Air Quality for City: %s of the Country: %s",
                cityAirQuality.getCityName(), cityAirQuality.getCountryCode()));

        return new ResponseEntity<>(cityAirQuality, HttpStatus.OK);
    }

    @GetMapping("/cachestats")
    public ResponseEntity<CacheStats> getCacheStats() {
        LOG.info("Received Request for /cachestats");
        return new ResponseEntity<>(ServiceCache.getCacheStats(), HttpStatus.OK);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        LOG.info("Received Request for /cities");

        List<City> allCities = cityService.getAllCities();

        return new ResponseEntity<>(allCities, HttpStatus.OK);
    }
}
