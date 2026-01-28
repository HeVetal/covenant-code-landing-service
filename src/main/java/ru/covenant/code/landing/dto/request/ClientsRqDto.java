package ru.covenant.code.landing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.validation.ValidEmail;

@Getter
@Setter
public class ClientsRqDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @ValidEmail(allowedTlds = {"ru", "com", "net"})
    private String email;

    private String message;
}
