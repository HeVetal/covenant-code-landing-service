package ru.covenant.code.landing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.validation.ValidEmail;
import ru.covenant.code.landing.validation.ValidPhone;

@Getter
@Setter
public class ClientsRqDto {

    @NotBlank
    private String name;

    @NotBlank
    @ValidPhone()
    private String phone;

    @NotBlank
    @ValidEmail(allowedTlds = {"ru", "com", "net"})
    private String email;

    private String message;
}
