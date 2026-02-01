package ru.covenant.code.landing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.dto.response.ResponseWrapper;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;
import java.util.UUID;


/**
 * REST-контроллер для административного управления заявками клиентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final ClientsService clientsService;


    /**
     * Обновляет статус клиента.
     *
     * @param id                 идентификатор клиента
     * @param clientsStatusRqDto новый статус
     * @return обновленные данные клиента
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseWrapper<ClientsDetailsRsDto>> updateStatusClient(
            @PathVariable("id") UUID id,
            @RequestBody ClientsStatusRqDto clientsStatusRqDto) {

        ClientsDetailsRsDto clientsDetailsRsDto = clientsService.updateStatus(id, clientsStatusRqDto);
        return ResponseEntity.ok(ResponseWrapper.success(clientsDetailsRsDto));
    }


    /**
     * Возвращает список клиентов для админки.
     *
     * @return список клиентов
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ClientsAdminRsDto>>> getAllClients() {
        return ResponseEntity.ok(ResponseWrapper.success(clientsService.getAllAdminClients()));
    }


    /**
     * Удаляет клиента по идентификатору.
     *
     * @param id идентификатор клиента
     * @return пустой ответ
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteClient(@PathVariable("id") UUID id) {
        clientsService.delete(id);
        return ResponseEntity.ok(ResponseWrapper.success(null));
    }


    /**
     * Возвращает клиента по идентификатору.
     *
     * @param id идентификатор клиента
     * @return данные клиента
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ClientsAdminRsDto>> getClientById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseWrapper.success(clientsService.getAdminClientById(id)));
    }
}
