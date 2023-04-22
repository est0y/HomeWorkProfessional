package ru.otus.crm.service;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.est0y.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.List;


public class PerformanceTest {

    private DBServiceClient dbServiceWithoutCache;
    private DBServiceClient dbServiceClientWithCache;

    @BeforeEach
    void init() {
        var configuration = new Configuration().configure("test.hibernate.cfg.xml");
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceWithoutCache = new DbServiceClientImpl(transactionManager, clientTemplate);
        dbServiceClientWithCache = new DBServiceWithCache(
                new DbServiceClientImpl(transactionManager, clientTemplate),
                new MyCache<>());
    }

    private double timeInMillis(Runnable runnable) {
        double start = System.nanoTime();
        runnable.run();
        return (System.nanoTime() - start) / 1000_000;
    }

    @DisplayName("С кешем быстрее, чем без кеша")
    @Test
    void withCacheFasterThanWithoutCache() {
        dbServiceWithoutCache.saveClient(new Client(
                null,
                "dbServiceFirst",
                new Address(null,
                        "улица пушкина"),
                List.of(new Phone(null, "88005553535")))
        );
        dbServiceWithoutCache.getClient(1L);
        dbServiceClientWithCache.getClient(1L);
        var withoutCacheTime = timeInMillis(() -> dbServiceWithoutCache.getClient(1L));
        var withCacheTime = timeInMillis(() -> dbServiceClientWithCache.getClient(1L));
        System.out.printf("Without cache %f millis\n", withoutCacheTime);
        System.out.printf("With cache %f millis\n", withCacheTime);
        System.out.printf("With cache faster by %f millis\n", (withoutCacheTime - withCacheTime));
        Assertions.assertTrue(withCacheTime < withoutCacheTime);
    }
}
