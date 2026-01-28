package ru.covenant.code.landing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.covenant.code.landing.validation.MaxLength;
import ru.covenant.code.landing.validation.ValidEmail;

@Getter
@Setter
public class ClientsRqDto {

    @NotBlank
    @MaxLength(value = 50, message = "Поле name должно быть не длиннее 50 символов")
    private String name;

    @NotBlank
    @MaxLength(value = 20, message = "Поле phone должно быть не длиннее 20 символов")
    private String phone;

    @NotBlank
    @MaxLength(value = 100, message = "Поле email должно быть не длиннее 100 символов")
    @ValidEmail(allowedTlds = {"ru", "com", "net"})
    private String email;

    @MaxLength(value = 1000, message = "Поле message должно быть не длиннее 1000 символов")
    private String message;
}
