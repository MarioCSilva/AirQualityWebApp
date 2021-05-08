package tqs.airquality.model;

import lombok.Getter;
import lombok.Setter;
import tqs.airquality.cache.Cache;

import java.io.Serializable;

@Getter
@Setter
public class CacheStats implements Serializable {
    private int hits;
    private int misses;
    private long timeToLive;
    private int totalChecks;

    public CacheStats() {
        this.hits = Cache.getHits();
        this.misses = Cache.getMisses();
        this.timeToLive = Cache.getTimeToLive();
        this.totalChecks = Cache.getTotalChecks();
    }

}
