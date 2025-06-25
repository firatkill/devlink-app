package com.worksOnLocal.DevLink.controller;

import com.worksOnLocal.DevLink.dto.request.CreateUserRequestDTO;
import com.worksOnLocal.DevLink.dto.request.LoginRequestDTO;
import com.worksOnLocal.DevLink.dto.response.ApiResponse;
import com.worksOnLocal.DevLink.dto.response.LoginResponseDTO;
import com.worksOnLocal.DevLink.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO){

        authService.createUser(createUserRequestDTO);


        return ResponseEntity.ok(ApiResponse.success("Kullanıcı başarıyla oluşturuldu",null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){

        LoginResponseDTO data= authService.loginUser(loginRequestDTO);

        ApiResponse<LoginResponseDTO> response = ApiResponse.success("Giriş başarılı",data);

        return ResponseEntity.ok(response);


    }

}
