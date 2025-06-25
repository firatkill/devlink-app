package com.worksOnLocal.DevLink.exception;

import com.worksOnLocal.DevLink.dto.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.failure("Bu kullanıcı adı ile kayıtlı kullanıcı zaten mevcut",e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.failure("Bu e-posta ile kayıtlı kullanıcı zaten mevcut",e.getMessage()));
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleWrongCredentialsException(WrongCredentialsException e) {
        return ResponseEntity.status(401).body(ApiResponse.failure("Giriş Bilgileri Hatalı",e.getMessage()));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(ApiResponse.failure("Hatalı istek.",e.getMessage()));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(ApiResponse.failure("Kaynak bulunamadı.",e.getMessage()));
    }
}
