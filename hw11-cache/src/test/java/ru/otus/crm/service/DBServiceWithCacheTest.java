package ru.otus.crm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.est0y.cachehw.MyCache;
import ru.otus.crm.model.Client;

import java.util.Optional;

import static java.time.Duration.ofSeconds;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DBServiceWithCacheTest {
    private DBServiceWithCache dbServiceWithCache;

    @BeforeEach
    void init() {
        DBServiceClient mock = mock(DBServiceClient.class);
        when(mock.saveClient(any())).thenAnswer((Answer<Client>) invocation -> {
            delay();
            return (Client) invocation.getArguments()[0];
        });
        when(mock.getClient(1L)).thenAnswer((Answer<Optional<Client>>) invocation -> {
            delay();
            return Optional.of(new Client(1L, "1"));
        });
        dbServiceWithCache = new DBServiceWithCache(mock, new MyCache<>());
    }

    private void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Получаем сохраненное значение из кеша,а не из базы")
    @Test
    void getClient() {
        dbServiceWithCache.saveClient(new Client(1L, "1"));
        Assertions.assertTimeout(ofSeconds(1), () -> dbServiceWithCache.getClient(1L));
    }
}