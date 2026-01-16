package ru.covenant.code.landing.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.entity.Clients;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.exceptions.ClientsNotFoundException;
import ru.covenant.code.landing.exceptions.PersistenceException;
import ru.covenant.code.landing.mapper.ClientsMapper;
import ru.covenant.code.landing.repository.ClientsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientsServiceImplTest {

    @Mock
    private ClientsRepository clientsRepository;

    @Mock
    private ClientsMapper clientsMapper;

    @InjectMocks
    private ClientsServiceImpl clientsService;


    private UUID id;
    private UUID testWrongID;
    private Clients clients;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        testWrongID = UUID.fromString("22222222-2222-2222-2222-222222222222");
        clients = new Clients();
        clients.setId(id);
        clients.setName("Ivan");
        clients.setEmail("client@example.com");
    }

    @Test
    void getByIdIfNull() {
        assertThrows(IllegalArgumentException.class, () -> clientsService.getById(null));
    }

    @Test
    void getByIdIfClientsNotFound() {
        assertThrows(ClientsNotFoundException.class, () -> clientsService.getById(id));
    }

    @Test
    void getByIdIfSucceed() {
        when(clientsRepository.findById(id))
                .thenReturn(Optional.of(clients));

        Clients clientsById = clientsService.getById(id);
        assertEquals(clients, clientsById);

    }

    @Test
    void createIfWrongParam() {
        ClientsRqDto request = new ClientsRqDto();
        request.setName("xxx");
        request.setEmail("test@example.com");

        when(clientsMapper.mapToClients(request))
                .thenReturn(clients);

        when(clientsRepository.save(clients))
                .thenThrow(new RuntimeException("ошибка БД"));

        assertThrows(PersistenceException.class, () -> clientsService.create(request));
    }

    @Test
    void createIfNullParam() {
        assertThrows(IllegalArgumentException.class, () -> clientsService.create(null));
    }


    /*@Test*/
   /* void createIfSucceed() {
        ClientsRqDto request = new ClientsRqDto();
        request.setName("Ivan");
        request.setEmail("client@example.com");
        when(clientsMapper.mapToClients(request))
                .thenReturn(clients);

        when(clientsRepository.save(clients))
                .thenReturn(clients);

        Clients clientsCreate = clientsService.create(request);

        assertEquals(clientsCreate, clients);
    }*/

    @Test
    void getAllIFListEmpty() {
        List<Clients> all = new ArrayList<>();
        assertEquals(all, clientsService.getAll());
    }

    @Test
    void getAllIFListNotEmpty() {
        List<Clients> oneClients = List.of(clients);
        when(clientsRepository.findAll())
                .thenReturn(oneClients);
        assertEquals(oneClients, clientsService.getAll());
    }

    @Test
    void updateStatusIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> clientsService.updateStatus(null, new ClientsStatusRqDto()));
    }

    @Test
    void updateStatusIfStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> clientsService.updateStatus(id, null));
    }


    @Test
    void updateStatus() {
        ClientsStatusRqDto dto = new ClientsStatusRqDto();
        dto.setStatus(Status.PROCESSED);

        Clients testStatusClients = new Clients();
        testStatusClients.setName("Ivan");
        testStatusClients.setEmail("client@example.com");
        testStatusClients.setStatus(Status.PROCESSED);

        clients.setStatus(Status.NEW);

        when(clientsRepository.findById(id))
                .thenReturn(Optional.of(clients));

        when(clientsRepository.save(any(Clients.class)))
                .thenReturn(testStatusClients);

        Clients resultClient = clientsService.updateStatus(id, dto);

        assertEquals(Status.PROCESSED, resultClient.getStatus());

    }

    @Test
    void deleteIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> clientsService.delete(null));
    }

    @Test
    void deleteIfWrongId() {
        assertThrows(ClientsNotFoundException.class, () -> clientsService.delete(id));
    }


}