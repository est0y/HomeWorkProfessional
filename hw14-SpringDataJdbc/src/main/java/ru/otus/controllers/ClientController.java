package ru.otus.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;

@AllArgsConstructor
@Controller
public class ClientController {
    private final DBServiceClient clientService;

    @GetMapping({"/", "/clients"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView clientSave(@RequestBody Client client) {
        clientService.saveClient(client);
        return new RedirectView("/", true);
    }

}
