package tqs.airquality.model;

import lombok.Getter;
import lombok.Setter;
import tqs.airquality.cache.ServiceCache;

import java.io.Serializable;

@Getter
@Setter
public class CacheStats implements Serializable {
    private int hits;
    private int misses;
    private long timeToLive;
    private int totalChecks;

    public CacheStats() {
        this.hits = ServiceCache.getHits();
        this.misses = ServiceCache.getMisses();
        this.timeToLive = ServiceCache.getTimeToLive();
        this.totalChecks = ServiceCache.getTotalChecks();
    }

}
