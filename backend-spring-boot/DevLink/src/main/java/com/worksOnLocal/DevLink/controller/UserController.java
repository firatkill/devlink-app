package com.worksOnLocal.DevLink.controller;

import com.worksOnLocal.DevLink.dto.request.UpdateUserRequestDTO;
import com.worksOnLocal.DevLink.dto.response.ApiResponse;
import com.worksOnLocal.DevLink.dto.response.UpdateUserResponseDTO;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    @PutMapping
    public ResponseEntity<ApiResponse<UpdateUserResponseDTO>> updateUser(@RequestBody @Valid UpdateUserRequestDTO updateUserRequestDTO, @AuthenticationPrincipal User user) {
    UpdateUserResponseDTO data=userService.updateUser(updateUserRequestDTO,user);
        ApiResponse<UpdateUserResponseDTO> response=ApiResponse.success("Kullanıcı bilgileri güncellendi",data);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);

        return ResponseEntity.ok(ApiResponse.success("Kullanıcı başarıyla silindi",null));
    }
}
