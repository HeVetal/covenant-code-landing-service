package ru.covenant.code.landing.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ClientsRsDto {

    private String name;

    private String phone;

    private String email;

    private String message;

}