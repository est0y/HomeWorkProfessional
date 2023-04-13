package ru.est0y.cachehw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

class MyCacheTest {
    private final static long START_KEY = 1L;
    private final static long END_KEY = 100L;
    private HwCache<String, Object> hwCache;

    @BeforeEach
    void init() {
        hwCache = new MyCache<>();
        loopWithKeys((key) -> hwCache.put(key, new Object()));
    }

    @DisplayName("Кеш отчищается после запуска gc")
    @Test
    void clearCache() throws InterruptedException {
        loopWithKeys((key) -> Assertions.assertNotNull(hwCache.get(key)));
        System.gc();
        Thread.sleep(100);
        loopWithKeys((key) -> Assertions.assertNull(hwCache.get(key)));
    }

    private void loopWithKeys(Consumer<String> consumer) {
        for (long key = START_KEY; key < END_KEY; key++) {
            consumer.accept(String.valueOf(key));
        }
    }
}