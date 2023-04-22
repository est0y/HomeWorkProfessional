package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.est0y.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceWithCache;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClientWithCache = new DBServiceWithCache(
                new DbServiceClientImpl(transactionManager, clientTemplate),
                new MyCache<>());
        for (int i = 1; i < 5; i++) {
            dbServiceClientWithCache.saveClient(new Client(
                    null, "Client" + i,
                    new Address(null,
                            "улица пушкина"),
                    List.of(new Phone(null, "88005553535"),
                            new Phone(null, "777")
                    )
            ));
        }
        dbServiceClientWithCache.getClient(3L);
        dbServiceClientWithCache.findAll();
    }
}
