package ru.covenant.code.landing.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.entity.enumerated.Status;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
public class ClientsDetailsRs {

    private UUID id;

    private String name;

    private String phone;

    private Status status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
