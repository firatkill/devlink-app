package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.UpdateExternalLinksRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProfileHeaderRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProfileImageRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateSocialLinksRequestDTO;
import com.worksOnLocal.DevLink.dto.response.*;
import com.worksOnLocal.DevLink.entity.Profile;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.enums.Platform;
import com.worksOnLocal.DevLink.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;


    private User user;
    private Profile profile;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUsername("firatkil");
        user.setEmail("firatkil@gmail.com");
        user.setId(1L);

        profile = new Profile();
        profile.setId(1L);
        profile.setHidden(false);
        profile.setUser(user);
        profile.setSocialLinks(new ArrayList<>());
        profile.setProjects(new ArrayList<>());
        profile.setExternalLinks(new ArrayList<>());
    }

    @Test
    void createBlankProfileWithUser_success(){

        profileService.createBlankProfileWithUser(user);

        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);
        Mockito.verify(profileRepository).save(captor.capture());
        Profile profile = captor.getValue();

        assertNotNull(profile);
        assertEquals(user,profile.getUser());

    }

    @Test
    void hideProfileByUserId_success(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        profileService.hideProfileByUserId(user.getId());

        assertTrue(profile.isHidden());
        Mockito.verify(profileRepository).save(profile);
    }

    @Test
    void hideProfileByUserId_throwsErrorWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.hideProfileByUserId(user.getId()));
        Mockito.verify(profileRepository, Mockito.never()).save(profile);
    }


    @Test
    void showProfileByUserId_success(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        profileService.showProfileByUserId(user.getId());

        assertFalse(profile.isHidden());
        Mockito.verify(profileRepository).save(profile);
    }

    @Test
    void showProfileByUserId_throwsErrorWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.showProfileByUserId(user.getId()));
        Mockito.verify(profileRepository, Mockito.never()).save(profile);
    }

    @Test
    void updateSocialLinks_success(){

        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any())).thenReturn(profile);

        List<UpdateSocialLinksRequestDTO> requestDTOs=List.of(
                new UpdateSocialLinksRequestDTO("github.com/firatkil", Platform.GITHUB,false)
        ) ;

        List<UpdateSocialLinksResponseDTO> result = profileService.updateSocialLinks(requestDTOs,user);

        assertEquals(1, result.size());
        Mockito.verify(profileRepository).save(profile);

    }

    @Test
    void updateSocialLinks_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.updateSocialLinks(List.of(),user));
        Mockito.verify(profileRepository, Mockito.never()).save(profile);
    }
    @Test
    void UpdateExternalLinks_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.updateExternalLinks(List.of(),user));
        Mockito.verify(profileRepository, Mockito.never()).save(profile);
    }

    @Test
    void updateExternalLinks_success(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any())).thenReturn(profile);



        List<UpdateExternalLinksRequestDTO> requestDTOs=List.of(
                new UpdateExternalLinksRequestDTO(null, "icon","https://site.com",false)
        ) ;

        List<UpdateExternalLinksResponseDTO> result= profileService.updateExternalLinks(requestDTOs,user);
        assertEquals(1, result.size());
        Mockito.verify(profileRepository).save(profile);

    }

    @Test
    void getProfileByUsername_success(){
        Mockito.when(profileRepository.findByUser_Username(user.getUsername())).thenReturn(Optional.of(profile));

        GetProfileResponseDTO dto = profileService.getProfileByUsername(user.getUsername());
        assertNotNull(dto);
    }

    @Test
    void getProfileByUsername_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Username(user.getUsername())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.getProfileByUsername(user.getUsername()));

    }

    @Test
    void updateProfileImage_success(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any())).thenReturn(profile);

        UpdateProfileImageRequestDTO dto = new UpdateProfileImageRequestDTO("image");
        UpdateProfileImageResponseDTO result = profileService.updateProfileImage(dto,user);

        assertNotNull(result);

    }
    @Test
    void updateProfileImage_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        UpdateProfileImageRequestDTO dto = new UpdateProfileImageRequestDTO("image");

        assertThrows(EntityNotFoundException.class, () -> profileService.updateProfileImage(dto,user));
    }

    @Test
    void updateProfileHeader_success(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any())).thenReturn(profile);
        UpdateProfileHeaderRequestDTO dto = new UpdateProfileHeaderRequestDTO("name","title","description");
        UpdateProfileHeaderResponseDTO result = profileService.updateProfileHeader(dto,user);

        assertNotNull(result);
        assertEquals("name",result.displayName());
        assertEquals("title",result.headerTitle());
        assertEquals("description",result.headerDescription());
    }

    @Test
    void updateProfileHeader_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        UpdateProfileHeaderRequestDTO dto = new UpdateProfileHeaderRequestDTO("name","title","description");
        assertThrows(EntityNotFoundException.class,()-> profileService.updateProfileHeader(dto,user));

    }


}