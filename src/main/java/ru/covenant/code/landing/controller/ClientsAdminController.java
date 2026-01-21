package ru.covenant.code.landing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;


@RestController
@RequestMapping("/admin/clients")
@RequiredArgsConstructor
public class ClientsAdminController {
    private final ClientsService clientsService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Clients> getAll() {
        return clientsService.getAll();
    }
}
