package ru.covenant.code.landing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsCreateRsDto;
import ru.covenant.code.landing.service.ClientsService;


@RestController
@RequestMapping("/v1/api/clients")
public class ClientsController {
    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @PostMapping
    public ResponseEntity<ClientsCreateRsDto> addClients(@RequestBody ClientsRqDto clientsRq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientsService.create(clientsRq));
    }
}
