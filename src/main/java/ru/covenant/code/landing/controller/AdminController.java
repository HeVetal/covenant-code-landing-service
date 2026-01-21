package ru.covenant.code.landing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.request.ClientsDetailsRs;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/clients")
public class AdminController {
    private final ClientsService clientsService;

    public AdminController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ClientsDetailsRs> updateClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsStatusRqDto clientsStatusRqDto) {

        ClientsDetailsRs clientsDetailsRs = clientsService.updateStatus(id, clientsStatusRqDto);
        return ResponseEntity.ok(clientsDetailsRs);
    }

    @GetMapping
    public ResponseEntity<List<ClientsAdminRsDto>> getAllClients() {
        return ResponseEntity.ok(clientsService.getAllAdminClients());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") UUID id) {
        clientsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Clients> getAll() {
        return clientsService.getAll();
    }
}
