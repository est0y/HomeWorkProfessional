package ru.otus.crm.service;

import ru.est0y.cachehw.HwCache;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DBServiceWithCache implements DBServiceClient {
    private final DBServiceClient dbServiceClient;
    private final HwCache<String, Client> hwCache;

    public DBServiceWithCache(DBServiceClient dbServiceClient, HwCache<String, Client> hwCache) {
        this.dbServiceClient = dbServiceClient;
        this.hwCache = hwCache;
    }

    @Override
    public Client saveClient(Client client) {
        Client savedClient = dbServiceClient.saveClient(client.clone());
        hwCache.put(String.valueOf(savedClient.getId()), savedClient);
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = hwCache.get(String.valueOf(id));
        if (client != null) {
            return Optional.of(client.clone());
        } else {
            Optional<Client> clientFromBd = dbServiceClient.getClient(id);
            clientFromBd.ifPresent(value -> hwCache.put(String.valueOf(value.getId()), value.clone()));
            return clientFromBd;
        }
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }
}
