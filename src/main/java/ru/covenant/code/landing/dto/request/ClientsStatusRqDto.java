package ru.covenant.code.landing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.entity.enumerated.Status;

@Getter
@Setter
public class ClientsStatusRqDto {

    @NotNull
    private Status status;
}