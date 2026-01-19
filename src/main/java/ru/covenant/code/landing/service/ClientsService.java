package ru.covenant.code.landing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.mapper.ClientsMapper;
import ru.covenant.code.landing.repository.ClientsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientsService {

    private final ClientsRepository clientsRepository;
    private final ClientsMapper clientsMapper;

    public List<ClientsAdminRsDto> getAllAdminClients() {
        return clientsRepository.findAll().stream()
                .map(clientsMapper::mapToClientsAdminRsDto)
                .toList();
    }
}
