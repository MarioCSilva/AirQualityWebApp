package tqs.airquality.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.model.CacheObjDetails;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceCacheTest {

    @Test
    void testRequestCached() {
        ServiceCache.cacheRequest("cities", "Aveiro");

        assertTrue(ServiceCache.isRequestCached("cities"));

        CacheObjDetails cachedReq = ServiceCache.checkCache("cities");
        assertTrue(cachedReq.getFound());
        assertEquals("Aveiro", cachedReq.getReturnValue());
    }

    @Test
    void testRequestExpired() throws InterruptedException {
        ServiceCache.cacheRequest("test_timer", 0);

        await().atMost(ServiceCache.getTimeToLive() + 1000, TimeUnit.MILLISECONDS).until(() ->
                ServiceCache.isRequestExpired("test_timer")
        );

        CacheObjDetails cachedReq = ServiceCache.checkCache("test_timer");
        assertFalse(cachedReq.getFound());
        assertEquals(null, cachedReq.getReturnValue());
    }

    @Test
    void whenRequestCached_thenReturnValueAndCheckStats() {
        int numHitsBefore = ServiceCache.getHits();
        int numMissesBefore = ServiceCache.getMisses();
        int numChecksBefore = ServiceCache.getTotalChecks();

        ServiceCache.cacheRequest("test", 0);
        assertTrue(ServiceCache.checkCache("test").getFound());

        int numHitsAfter = ServiceCache.getHits();
        int numMissesAfter = ServiceCache.getMisses();
        int numChecksAfter = ServiceCache.getTotalChecks();

        assertEquals(numHitsBefore + 1, numHitsAfter);
        assertEquals(numMissesBefore, numMissesAfter);
        assertEquals(numChecksBefore + 1, numChecksAfter);
    }

    @Test
    void whenRequestNotCached_thenReturnValueAndCheckStats() {
        int numHitsBefore = ServiceCache.getHits();
        int numMissesBefore = ServiceCache.getMisses();
        int numChecksBefore = ServiceCache.getTotalChecks();

        assertFalse(ServiceCache.checkCache("test_not_cached").getFound());

        int numHitsAfter = ServiceCache.getHits();
        int numMissesAfter = ServiceCache.getMisses();
        int numChecksAfter = ServiceCache.getTotalChecks();

        assertEquals(numHitsBefore, numHitsAfter);
        assertEquals(numMissesBefore + 1, numMissesAfter);
        assertEquals(numChecksBefore + 1, numChecksAfter);
    }
}