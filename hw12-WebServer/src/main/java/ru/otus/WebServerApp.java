package ru.otus;

import org.hibernate.cfg.Configuration;
import ru.otus.data.core.repository.DataTemplateHibernate;
import ru.otus.data.core.repository.HibernateUtils;
import ru.otus.data.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.data.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.data.crm.model.Address;
import ru.otus.data.crm.model.Client;
import ru.otus.data.crm.model.Phone;
import ru.otus.data.crm.service.DBServiceClient;
import ru.otus.data.crm.service.DbServiceClientImpl;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.AdminAuthService;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/clients
*/
public class WebServerApp {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private static DBServiceClient dbServiceInit() {
        var configuration = new Configuration().configure("hibernate.cfg.xml");
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = dbServiceInit();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new AdminAuthService();
        ClientsWebServer clientsWebServer = new ClientsWebServerImpl(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);
        clientsWebServer.start();
        clientsWebServer.join();
    }
}
