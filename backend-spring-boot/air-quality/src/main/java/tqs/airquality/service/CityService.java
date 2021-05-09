package tqs.airquality.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tqs.airquality.cache.ServiceCache;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.City;
import tqs.airquality.repository.CityRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Service
public class CityService {

    private static final Logger LOG = LogManager.getLogger(CityService.class);

    @Autowired
    CityRepository cityRepository;

    public List<City> getAllCities() {
        LOG.info("Fetching all cities from City Repository");

        CacheObjDetails cacheObjDetails = ServiceCache.checkCache("cities");
        List<City> cities = (List<City>) cacheObjDetails.getReturnValue();

        if (Boolean.FALSE.equals(cacheObjDetails.getFound())) {
            cities = cityRepository.findAll();

            // load csv and save on repository
            if(cities.isEmpty()) {
                LOG.info("Loading Cities into City Repository");
                String[] rows = null;
                try {
                    File resource = new ClassPathResource("cities_data.csv").getFile();
                    rows = (new String(Files.readAllBytes(resource.toPath()))).split("\n");
                } catch (IOException e) {
                    LOG.error("Something Went Wrong While Trying to Read CSV Data.");
                    return Collections.emptyList();
                }
                for (String c : rows) {
                    String[] fields = c.split(",");
                    City city = new City(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], fields[4],
                            Double.parseDouble(fields[5]), Double.parseDouble(fields[6]));
                    cityRepository.save(city);
                    cities.add(city);
                }
            }

        }
        ServiceCache.cacheRequest("cities", cities);
        return cities;
    }
}