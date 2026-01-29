package ru.covenant.code.landing.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.dto.response.ResponseWrapper;
import ru.covenant.code.landing.service.ClientsService;


/**
 * REST-контроллер для обработки заявок клиентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/clients")
public class ClientsController {

    private final ClientsService clientsService;

    /**
     * Создает новую заявку клиента.
     *
     * @param clientsRq данные заявки
     * @return ответ с данными заявки
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper<ClientsDetailsRsDto>> addClients(@Valid @RequestBody ClientsRqDto clientsRq) {
        ClientsDetailsRsDto clientsCreateRsDto = clientsService.create(clientsRq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(clientsCreateRsDto));
    }
}
