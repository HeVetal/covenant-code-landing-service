package ru.covenant.code.landing.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.dto.response.ResponseWrapper;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admin/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final ClientsService clientsService;

    public AdminController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseWrapper<ClientsDetailsRsDto>> updateStatusClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsStatusRqDto clientsStatusRqDto) {

        ClientsDetailsRsDto clientsDetailsRsDto = clientsService.updateStatus(id, clientsStatusRqDto);
        return ResponseEntity.ok(ResponseWrapper.success(clientsDetailsRsDto));
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ClientsAdminRsDto>>> getAllClients() {
        return ResponseEntity.ok(ResponseWrapper.success(clientsService.getAllAdminClients()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteClient(@PathVariable("id") UUID id) {
        clientsService.delete(id);
        return ResponseEntity.ok(ResponseWrapper.success(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ClientsAdminRsDto>> getClientById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseWrapper.success(clientsService.getAdminClientById(id)));
    }
}
