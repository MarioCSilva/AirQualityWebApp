package tqs.airquality.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tqs.airquality.model.CityAirQuality;

import java.util.HashMap;
import java.util.Map;

public class AirQualityCache {
    private static final Logger LOG = LogManager.getLogger(AirQualityCache.class);
    private long timeToLive;
    private int numberOfRequests;
    private int numberOfHits;
    private int numberOfMisses;

    @JsonIgnore
    private Map<String, CityAirQuality> requests;

    @JsonIgnore
    private Map<String, Long> requestsExpiration;

    public AirQualityCache(long defaultExpire) {
        this.requests = new HashMap<>();
        this.requestsExpiration = new HashMap<>();
        this.timeToLive = defaultExpire;
    }

    public void storeRequest(String name, CityAirQuality obj) {
        this.requests.put(name, obj);
        this.requestsExpiration.put(name, getCurrentTimeInMillis() + this.timeToLive * 1000);
    }

    public CityAirQuality getRequest(String name) {
        this.numberOfRequests++;
        CityAirQuality request = null;

        if (!exists(name)) {
            this.numberOfMisses++;
        } else if (hasExpired(name)) {
            this.removeExpiredRequest(name);
            this.numberOfMisses++;
        } else {
            this.numberOfHits++;
            request = this.requests.get(name);
        }

        return request;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    private boolean exists(String name) {
        return this.requestsExpiration.containsKey(name);
    }

    private boolean hasExpired(String name) {
        Long expireTime = this.requestsExpiration.get(name);
        return getCurrentTimeInMillis() > expireTime;
    }

    private void removeExpiredRequest(String name) {
        this.requests.remove(name);
        this.requestsExpiration.remove(name);
    }

    private long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }
}
