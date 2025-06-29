package com.worksOnLocal.DevLink.controller;

import com.worksOnLocal.DevLink.dto.request.*;
import com.worksOnLocal.DevLink.dto.response.*;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.service.ProfileService;
import com.worksOnLocal.DevLink.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final ProjectService projectService;
    // profil gizleme, linkleri güncelleme, projeleri güncelleme

    @PutMapping("/hide-profile")
    public ResponseEntity<ApiResponse<Void>> hideProfile(@AuthenticationPrincipal User user) {
        profileService.hideProfileByUserId(user.getId());

        return ResponseEntity.ok(ApiResponse.success("Profil Gizlendi",null));
    }
    @PutMapping("/show-profile")
    public ResponseEntity<ApiResponse<Void>> showProfile(@AuthenticationPrincipal User user) {
        profileService.showProfileByUserId(user.getId());

        return ResponseEntity.ok(ApiResponse.success("Profil Herkese Açık Hale Getirildi",null));
    }

    @PutMapping("/social-links")
    public ResponseEntity<ApiResponse<List<UpdateSocialLinksResponseDTO>>> updateSocialLinks(@RequestBody List<UpdateSocialLinksRequestDTO> updateSocialLinksRequestDTOs, @AuthenticationPrincipal User user) {
        List<UpdateSocialLinksResponseDTO> data = profileService.updateSocialLinks(updateSocialLinksRequestDTOs,user);
        return ResponseEntity.ok(ApiResponse.success("Sosyal medya bağlantıları güncellendi",data));


    }

    @PutMapping("/external-links")
    public ResponseEntity<ApiResponse<List<UpdateExternalLinksResponseDTO>>> updateExternalLinks
            (@RequestBody @Valid List<UpdateExternalLinksRequestDTO> updateExternalLinksRequestDTOs , @AuthenticationPrincipal User user){

        List<UpdateExternalLinksResponseDTO> data=profileService.updateExternalLinks(updateExternalLinksRequestDTOs,user);
        return ResponseEntity.ok(ApiResponse.success("Dış bağlantılar güncellendi",data));

    }

    @PutMapping("/update-image")
    public ResponseEntity<ApiResponse<UpdateProfileImageResponseDTO>> updateProfileImage(@RequestBody UpdateProfileImageRequestDTO updateProfileImageRequestDTO , @AuthenticationPrincipal User user){
        UpdateProfileImageResponseDTO data = profileService.updateProfileImage(updateProfileImageRequestDTO,user);

        return ResponseEntity.ok(ApiResponse.success("Profil Fotoğrafı Güncellendi.",data));

    }
    @PutMapping("/update-header")
    public ResponseEntity<ApiResponse<UpdateProfileHeaderResponseDTO>> updateProfileHeader(@RequestBody UpdateProfileHeaderRequestDTO updateProfileHeaderRequestDTO, @AuthenticationPrincipal User user){

        UpdateProfileHeaderResponseDTO data =profileService.updateProfileHeader(updateProfileHeaderRequestDTO,user);

        return ResponseEntity.ok(ApiResponse.success("Profil Bilgileri güncellendi",data));
    }

    @PutMapping("/projects")
    public ResponseEntity<ApiResponse<UpdateProjectResponseDTO>> updateProject(@RequestBody UpdateProjectRequestDTO updateProjectRequestDTO, @AuthenticationPrincipal User user){

        UpdateProjectResponseDTO data = projectService.updateProject(updateProjectRequestDTO,user);
        return ResponseEntity.ok(ApiResponse.success("Proje Bilgileri Güncellendi",data));

    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId,@AuthenticationPrincipal User user){

        projectService.deleteProject(projectId,user);
        return ResponseEntity.ok(ApiResponse.success("Proje Silindi",null));

    }
    @PostMapping("/projects")
    public ResponseEntity<ApiResponse<CreateProjectResponseDTO>> createProject(@RequestBody CreateProjectRequestDTO createProjectRequestDTO, @AuthenticationPrincipal User user){
        CreateProjectResponseDTO data = projectService.createProject(createProjectRequestDTO,user);

        return ResponseEntity.ok(ApiResponse.success("Proje Oluşturuldu",data));
    }

    @GetMapping("/get-views")
    public ResponseEntity<ApiResponse<Long>> getViews(@AuthenticationPrincipal User user){
        Long viewsCount=projectService.getViewsCount(user);
        return ResponseEntity.ok(ApiResponse.success("Görüntüleme Sayısı Başarıyla Getirildi.",viewsCount));
    }

}
