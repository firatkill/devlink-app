package com.worksOnLocal.DevLink.controller;

import com.worksOnLocal.DevLink.dto.response.ApiResponse;
import com.worksOnLocal.DevLink.dto.response.GetProfileResponseDTO;
import com.worksOnLocal.DevLink.service.ProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class publicController {
    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<GetProfileResponseDTO>> getProfile(@PathVariable String username) {

        GetProfileResponseDTO data = profileService.getProfileByUsername(username);

        return ResponseEntity.ok(ApiResponse.success("Profil Başarıyla getirildi",data));

    }
}
