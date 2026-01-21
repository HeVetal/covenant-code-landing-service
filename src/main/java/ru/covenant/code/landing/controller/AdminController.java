package ru.covenant.code.landing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.entity.enumerated.Status;
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
    public ResponseEntity<ClientsDetailsRsDto> updateStatusClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsStatusRqDto clientsStatusRqDto) {

        ClientsDetailsRsDto clientsDetailsRsDto = clientsService.updateStatus(id, clientsStatusRqDto);
        return ResponseEntity.ok(clientsDetailsRsDto);
    }

    @GetMapping
    public String clientsPage(Model model) {
        model.addAttribute("clients", clientsService.getAllAdminClients());
        return "admin/clients";
    }

    @PostMapping("/{id}/process")
    public String processClient(@PathVariable UUID id) {
        ClientsStatusRqDto clientStatus = new ClientsStatusRqDto();
        clientStatus.setStatus(Status.PROCESSED);
        clientsService.updateStatus(id, clientStatus);
        return "redirect:/admin/clients";
    }

    @PostMapping("/{id}/delete")
    public String deleteClientFromPage(@PathVariable UUID id) {
        clientsService.delete(id);
        return "redirect:/admin/clients";
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

    @GetMapping("/{id}")
    public ResponseEntity<ClientsAdminRsDto> getClientById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientsService.getAdminClientById(id));
    }
}
