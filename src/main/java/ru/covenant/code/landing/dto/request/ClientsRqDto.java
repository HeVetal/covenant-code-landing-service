package ru.covenant.code.landing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsRqDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @Email
    private String email;

    private String message;
}
