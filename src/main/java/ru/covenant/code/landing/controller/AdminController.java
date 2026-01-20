package ru.covenant.code.landing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.service.ClientsService;

import java.util.UUID;

@RestController
@RequestMapping("/admin/clients")
public class AdminController {
    private final ClientsService clientsService;

    public AdminController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ClientsAdminRsDto> updateClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsAdminRsDto clientsAdminRsDto) {

        return ResponseEntity.ok(clientsService.update(id, clientsAdminRsDto));
    }
}
