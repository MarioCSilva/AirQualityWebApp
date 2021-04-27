package tqs.airquality.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tqs.airquality.model.CacheObjDetails;
import tqs.airquality.model.CacheStats;

import java.util.HashMap;
import java.util.Map;

public class AirQualityCache {
    private static final Logger LOG = LogManager.getLogger(AirQualityCache.class);

    // GLOBAL VARIABLES
    public static long timeToLive = 60 * 1000;
    public static int totalChecks = 0;
    public static int hits = 0;
    public static int misses = 0;
    public static Map<String, Object> cache = new HashMap<>();
    public static Map<String, Long> cacheExpiration = new HashMap<>();

    public static void cacheRequest(String request, Object returnValue) {
        LOG.info("Caching Request and Updating TTL: " + request);
        cache.put(request, returnValue);
        cacheExpiration.put(request, System.currentTimeMillis() + timeToLive);
    }

    public static CacheObjDetails checkCache(String request) {
        LOG.info("Checking if Request is in Cache: " + request);
        totalChecks++;
        CacheObjDetails cacheObjDetails = new CacheObjDetails(false, null);
        if (!isRequestCached(request)) {
            LOG.info("(MISS) Request not Cached: " + request);
            misses++;
        } else if (isRequestExpired(request)) {
            LOG.info("(MISS Request Expired: " + request);
            deleteRequestFromCache(request);
            misses++;
        } else {
            LOG.info("(HIT) Request is Cached: " + request);
            hits++;
            return new CacheObjDetails(true, cache.get(request));
        }
        return cacheObjDetails;
    }

    private static boolean isRequestCached(String request) {
        return cache.containsKey(request);
    }

    private static boolean isRequestExpired(String request) {
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
}
