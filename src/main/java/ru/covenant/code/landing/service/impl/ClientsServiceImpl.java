package ru.covenant.code.landing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.dto.response.ClientsDetailsRsDto;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.exceptions.ClientsNotFoundException;
import ru.covenant.code.landing.exceptions.InvalidClientsStatusException;
import ru.covenant.code.landing.mapper.ClientsMapper;
import ru.covenant.code.landing.repository.ClientsRepository;
import ru.covenant.code.landing.service.ClientsService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientsServiceImpl implements ClientsService {

    private final ClientsRepository clientsRepository;
    private final ClientsMapper clientsMapper;

    @Override
    public Clients getById(UUID id) {
        return null;
    }

    @Transactional
    public ClientsDetailsRsDto create(ClientsRqDto clientsRqDto) {
        log.info("Creating new client: name={}, email={}", clientsRqDto.getName(), clientsRqDto.getEmail());

        Clients client = clientsMapper.mapToClients(clientsRqDto);
        client.setName(clientsRqDto.getName());
        client.setPhone(clientsRqDto.getPhone());
        client.setEmail(clientsRqDto.getEmail());
        client.setMessage(clientsRqDto.getMessage());

        Clients savedClient = clientsRepository.save(client);
        log.info("Client created with id: {}", savedClient.getId());

        return clientsMapper.mapToClientsDetailsRs(savedClient);
    }

    @Override
    public List<Clients> getAll() {
        return List.of();
    }

    // Метод для получения всех клиентов для админки
    public List<ClientsAdminRsDto> getAllAdminClients() {
        log.info("Getting all clients for admin");
        List<Clients> clients = clientsRepository.findAll();
        log.info("Found {} clients in repository", clients.size());

        List<ClientsAdminRsDto> result = clientsMapper.mapToClientsAdminRsDtoList(clients);

        // Отладочная информация
        if (!result.isEmpty()) {
            log.info("First client in result: id={}, name={}, email={}, createdAt={}",
                    result.get(0).getId(), result.get(0).getName(),
                    result.get(0).getEmail(), result.get(0).getCreatedAt());
        }

        return result;
    }

    // Метод для получения клиента по ID для админки
    public ClientsAdminRsDto getAdminClientById(UUID id) {
        log.info("Getting client by id for admin: {}", id);
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new ClientsNotFoundException("Client not found with id: " + id));

        ClientsAdminRsDto dto = clientsMapper.mapToClientsAdminRsDto(client);
        log.info("Found client: id={}, name={}, email={}", dto.getId(), dto.getName(), dto.getEmail());

        return dto;
    }

    @Transactional
    public ClientsDetailsRsDto updateStatus(UUID id, ClientsStatusRqDto clientsStatusRqDto) {
        log.info("Updating status for client id={} to {}", id, clientsStatusRqDto.getStatus());

        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new ClientsNotFoundException("Client not found with id: " + id));

        // Проверка допустимости изменения статуса
        if (!isValidStatusTransition(client.getStatus(), clientsStatusRqDto.getStatus())) {
            throw new InvalidClientsStatusException(
                    "Cannot change status from " + client.getStatus() + " to " + clientsStatusRqDto.getStatus()
            );
        }

        client.setStatus(clientsStatusRqDto.getStatus());
        Clients updatedClient = clientsRepository.save(client);

        return clientsMapper.mapToClientsDetailsRs(updatedClient);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deleting client with id: {}", id);
        if (!clientsRepository.existsById(id)) {
            throw new ClientsNotFoundException("Client not found with id: " + id);
        }
        clientsRepository.deleteById(id);
        log.info("Client deleted successfully");
    }

    private boolean isValidStatusTransition(Status currentStatus, Status newStatus) {
        // Простая валидация переходов статусов
        if (currentStatus == Status.NEW) {
            return newStatus == Status.PROCESSED;
        }
        if (currentStatus == Status.PROCESSED) {
            return newStatus == Status.NEW;
        }
        return false;
    }

    // Дополнительный метод для отладки - получить все "сырые" клиенты
    public List<Clients> getAllClientsRaw() {
        return clientsRepository.findAll();
    }
}