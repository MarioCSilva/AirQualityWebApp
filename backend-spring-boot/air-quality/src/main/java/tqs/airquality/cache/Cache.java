package tqs.airquality.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.CacheStats;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static final Logger LOG = LogManager.getLogger(Cache.class);

    private static long timeToLive = 30l * 1000l;
    private static int totalChecks = 0;
    private static int hits = 0;
    private static int misses = 0;
    private static Map<String, Object> cache = new HashMap<>();
    private static Map<String, Long> cacheExpiration = new HashMap<>();

    public static void cacheRequest(String request, Object returnValue) {
        LOG.info(String.format("Caching Request and Updating TTL: %s" , request));
        cache.put(request, returnValue);
        cacheExpiration.put(request, System.currentTimeMillis() + timeToLive);
    }

    public static CacheObjDetails checkCache(String request) {
        LOG.info(String.format("Checking if Request is in Cache: %s", request));
        totalChecks++;
        CacheObjDetails cacheObjDetails = new CacheObjDetails(false, null);
        if (!isRequestCached(request)) {
            LOG.info(String.format("(MISS) Request not Cached: %s", request));
            misses++;
        } else if (isRequestExpired(request)) {
            LOG.info(String.format("(MISS Request Expired: %s", request));
            deleteRequestFromCache(request);
            misses++;
        } else {
            LOG.info(String.format("(HIT) Request is Cached: %s", request));
            hits++;
            return new CacheObjDetails(true, cache.get(request));
        }
        return cacheObjDetails;
    }

    public static boolean isRequestCached(String request) {
        return cache.containsKey(request);
    }

    public static boolean isRequestExpired(String request) {
        Long expirationTime = cacheExpiration.get(request);
        return System.currentTimeMillis() > expirationTime;
    }

    private static void deleteRequestFromCache(String request) {
        cache.remove(request);
        cacheExpiration.remove(request);
    }

    public static CacheStats getCacheStats() {
        return new CacheStats();
    }

    public static long getTimeToLive() {
        return timeToLive;
    }

    public static int getTotalChecks() {
        return totalChecks;
    }

    public static int getHits() {
        return hits;
    }

    public static int getMisses() {
        return misses;
    }
}
