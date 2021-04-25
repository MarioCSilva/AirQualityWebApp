package tqs.airquality.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import tqs.airquality.model.CityAirQuality;
import tqs.airquality.service.AirQualityService;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/v1/")
public class AirQualityController {

    private static final Logger LOG = LogManager.getLogger(AirQualityController.class);

    @Autowired
    private AirQualityService service;

    @CrossOrigin
    @GetMapping("/airquality")
    public ResponseEntity<CityAirQuality> getAirMetrics(
            @RequestParam(value = "city") String city,
            @RequestParam(value = "country", required = false) String country) {
        LOG.info("Received Request for /airquality with params: city: " + city + " and country: " + country);
        Optional<CityAirQuality> cityAirData = service.getCityAirQuality(city, Optional.ofNullable(country));
        if (cityAirData.isEmpty()) {
            LOG.warn("Returning City Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CityAirQuality cityAirQuality = cityAirData.get();
        LOG.info("Returning Air Quality for City: " + cityAirQuality.getCityName()
                + " of the Country: " + cityAirQuality.getCountryCode());
        return new ResponseEntity<>(cityAirQuality, HttpStatus.OK);
    }
}
