package ru.covenant.code.landing.dto.response;

import ru.covenant.code.landing.entity.enumerated.Status;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ClientsListRsDto {
    private UUID id;

    private String name;

    private String phone;

    private String email;

    private String message;

    private Status status;

    private OffsetDateTime createdAt;

}
