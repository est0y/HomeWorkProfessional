package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.data.crm.model.Address;
import ru.otus.data.crm.model.Client;
import ru.otus.data.crm.model.Phone;
import ru.otus.data.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientsServlet extends HttpServlet {
    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String CLIENT_NAME_PARAM_KEY = "name";
    private static final String CLIENT_ADDRESS_PARAM_KEY = "address";
    private static final String CLIENT_PHONES_PARAM_KEY = "phones[]";
    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("clients", dbServiceClient.findAll());
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        var phonesStrings = Arrays.stream(req.getParameterMap().get(CLIENT_PHONES_PARAM_KEY))
                .filter(phoneString -> !phoneString.isEmpty()).toList();
        var client = new Client(req.getParameter(CLIENT_NAME_PARAM_KEY));
        client.setAddress(new Address(null, req.getParameter(CLIENT_ADDRESS_PARAM_KEY)));
        var phones = phonesStrings.stream().map(phone -> new Phone(null, phone)).toList();
        client.setPhones(phones);
        dbServiceClient.saveClient(client);
        response.sendRedirect("/clients");
    }
}

