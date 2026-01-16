package ru.covenant.code.landing.service;

import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsCreateRsDto;
import ru.covenant.code.landing.entity.Clients;

import java.util.List;
import java.util.UUID;

public interface ClientsService {
    public abstract Clients getById(UUID id);

    public abstract ClientsCreateRsDto create(ClientsRqDto request);

    public abstract List<Clients> getAll();

    public abstract Clients updateStatus(UUID id, ClientsStatusRqDto clientsStatusRqDto);

    public abstract void delete(UUID id);
}
