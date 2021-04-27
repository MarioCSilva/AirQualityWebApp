package tqs.airquality.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import tqs.airquality.cache.AirQualityCache;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class CacheStats implements Serializable {
    private int hits;
    private int misses;
    private long timeToLive;
    private int totalChecks;
    @JsonIgnore
    private Map<String, CityAirQuality> cache;
    @JsonIgnore
    private Map<String, Long> cacheExpiration;

    public CacheStats() {
        this.hits = AirQualityCache.hits;
        this.misses = AirQualityCache.misses;
        this.timeToLive = AirQualityCache.timeToLive;
        this.totalChecks = AirQualityCache.totalChecks;
    }

}
