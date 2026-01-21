package ru.covenant.code.landing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.request.ClientsDetailsRs;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/clients")
public class AdminController {

    private final ClientsService clientsService;

    public AdminController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    @ResponseBody
    @PutMapping("/{id}/status")
    public ResponseEntity<ClientsDetailsRs> updateClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsStatusRqDto clientsStatusRqDto) {

        ClientsDetailsRs clientsDetailsRs = clientsService.updateStatus(id, clientsStatusRqDto);
        return ResponseEntity.ok(clientsDetailsRs);
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


    @ResponseBody
    @GetMapping("/api")
    public ResponseEntity<List<ClientsAdminRsDto>> getAllClients() {
        return ResponseEntity.ok(clientsService.getAllAdminClients());
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") UUID id) {
        clientsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ClientsAdminRsDto> getClientById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientsService.getAdminClientById(id));
    }
}
