package ru.covenant.code.landing.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private final boolean success;
    private final T data;

    public static <T> ResponseWrapper<T> success(T data) {
        return new ResponseWrapper<>(true, data);
    }
}
