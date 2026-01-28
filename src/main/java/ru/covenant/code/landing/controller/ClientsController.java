package ru.covenant.code.landing.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.response.ClientsCreateRsDto;
import ru.covenant.code.landing.dto.response.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper<ClientsCreateRsDto>> addClients(@Valid @RequestBody ClientsRqDto clientsRq) {
        ClientsCreateRsDto clientsCreateRsDto = clientsService.create(clientsRq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(clientsCreateRsDto));
    }
}
