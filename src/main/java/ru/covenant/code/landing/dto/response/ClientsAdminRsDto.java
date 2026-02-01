package ru.covenant.code.landing.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.entity.enumerated.Status;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class ClientsAdminRsDto {

    private UUID id;

    private String name;

    private String phone;

    private String email;

    private String message;

    private Status status;

    private OffsetDateTime createdAt;
}
