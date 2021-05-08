package tqs.airquality.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.model.CacheObjDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CacheTest {

    @Test
    void requestCachedTest() {
        Cache.cacheRequest("cities", "Aveiro");

        assertTrue(Cache.isRequestCached("cities"));

        CacheObjDetails cachedReq = Cache.checkCache("cities");
        assertTrue(cachedReq.getFound());
        assertEquals("Aveiro", cachedReq.getReturnValue());
    }

    @Test
    void requestExpiredTest() throws InterruptedException {
        Cache.cacheRequest("test_timer", 0);
        Thread.sleep(Cache.getTimeToLive() + 1000);
        assertTrue(Cache.isRequestExpired("test_timer"));

        CacheObjDetails cachedReq= Cache.checkCache("test_timer");
        assertFalse(cachedReq.getFound());
        assertNotEquals(0, cachedReq.getReturnValue());
        assertEquals(null, cachedReq.getReturnValue());
    }

    @Test
    public void whenRequestCached_thenReturnValueAndCheckStats() {
        int numHitsBefore = Cache.getHits();
        int numMissesBefore = Cache.getMisses();
        int numChecksBefore = Cache.getTotalChecks();

        Cache.cacheRequest("test", 0);
        assertTrue(Cache.checkCache("test").getFound());

        int numHitsAfter = Cache.getHits();
        int numMissesAfter = Cache.getMisses();
        int numChecksAfter = Cache.getTotalChecks();

        assertEquals(numHitsBefore + 1, numHitsAfter);
        assertEquals(numMissesBefore, numMissesAfter);
        assertEquals(numChecksBefore + 1, numChecksAfter);
    }

    @Test
    public void whenRequestNotCached_thenReturnValueAndCheckStats() {
        int numHitsBefore = Cache.getHits();
        int numMissesBefore = Cache.getMisses();
        int numChecksBefore = Cache.getTotalChecks();

        assertFalse(Cache.checkCache("test_not_cached").getFound());

        int numHitsAfter = Cache.getHits();
        int numMissesAfter = Cache.getMisses();
        int numChecksAfter = Cache.getTotalChecks();

        assertEquals(numHitsBefore, numHitsAfter);
        assertEquals(numMissesBefore + 1, numMissesAfter);
        assertEquals(numChecksBefore + 1, numChecksAfter);
    }
}