package ru.covenant.code.landing.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import ru.covenant.code.landing.dto.response.ErrorResponse;
import ru.covenant.code.landing.exceptions.BusinessException;
import ru.covenant.code.landing.exceptions.InvalidClientsStatusException;
import ru.covenant.code.landing.exceptions.enumerated.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e,
                                                                  ServletWebRequest request){
        log.error("Ошибка бизнес логики" + e.getMessage());
        ErrorResponse response = new ErrorResponse(
                e.getErrorCode().name(),
                e.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException e,
            ServletWebRequest request
    ){
        log.warn("Ошибка валидации параметров");
        String[] fields = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toArray(String[]::new);

        ErrorResponse response = new ErrorResponse(
                ErrorCode.VALIDATION_ERROR.name(),
                "Ошибка валидации параметров",
                fields
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception e,
            ServletWebRequest request
    ){
        log.error("Произошла непредвиденная ошибка");

        ErrorResponse response = new ErrorResponse(
                ErrorCode.COMMON_ERROR.name(),
                "Произошла непредвиденная ошибка",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
}
