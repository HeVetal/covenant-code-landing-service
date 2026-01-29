package ru.covenant.code.landing.service;

import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.entity.Clients;

import java.util.List;
import java.util.UUID;

public interface ClientsService {

    Clients getById(UUID id);

    ClientsDetailsRsDto create(ClientsRqDto request);

    List<Clients> getAll();

    ClientsDetailsRsDto updateStatus(UUID id, ClientsStatusRqDto clientsStatusRqDto);

    void delete(UUID id);

    List<ClientsAdminRsDto> getAllAdminClients();

    ClientsAdminRsDto getAdminClientById(UUID id);

}