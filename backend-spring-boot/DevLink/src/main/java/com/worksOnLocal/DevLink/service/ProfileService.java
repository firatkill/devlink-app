package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.UpdateExternalLinksRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProfileHeaderRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProfileImageRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateSocialLinksRequestDTO;
import com.worksOnLocal.DevLink.dto.response.*;
import com.worksOnLocal.DevLink.entity.ExternalLink;
import com.worksOnLocal.DevLink.entity.Profile;
import com.worksOnLocal.DevLink.entity.SocialLink;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public void createBlankProfileWithUser(User savedUser) {

        Profile profile = new Profile();
        profile.setUser(savedUser);
        profileRepository.save(profile);

    }

    public void hideProfileByUserId(Long userId) {
        Profile profile=profileRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);

        profile.setHidden(true);
        profileRepository.save(profile);

    }

    public void showProfileByUserId(Long userId) {
        Profile profile=profileRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);

        profile.setHidden(false);
        profileRepository.save(profile);


    }

    public List<UpdateSocialLinksResponseDTO> updateSocialLinks(List<UpdateSocialLinksRequestDTO> updateSocialLinksRequestDTOs, User user) {
        Profile profile= profileRepository.findByUser_Id(user.getId()).orElseThrow(EntityNotFoundException::new);

        profile.getSocialLinks().clear();

        List<SocialLink> socialLinks=updateSocialLinksRequestDTOs.stream().map(dto->{
            SocialLink socialLink= new SocialLink();
            socialLink.setPlatform(dto.platform());
            socialLink.setUrl(dto.url());
            socialLink.setProfile(profile);
            socialLink.setHidden(dto.hidden());
            return socialLink;
        }).toList();

        profile.getSocialLinks().addAll(socialLinks);
        Profile savedProfile= profileRepository.save(profile);

        return savedProfile.getSocialLinks().stream().map(link->{
            return new UpdateSocialLinksResponseDTO(link.getId(),link.getUrl(),
                    link.getPlatform(),link.isHidden());
        }).toList();

    }


    public List<UpdateExternalLinksResponseDTO> updateExternalLinks(@Valid List<UpdateExternalLinksRequestDTO> updateExternalLinksRequestDTOs, User user) {

        Profile profile= profileRepository.findByUser_Id(user.getId()).orElseThrow(EntityNotFoundException::new);

        profile.getExternalLinks().clear();

        List<ExternalLink> externalLinks=updateExternalLinksRequestDTOs.stream().map(dto->{
            ExternalLink externalLink=new ExternalLink();

            externalLink.setUrl(dto.url());
            externalLink.setProfile(profile);
            externalLink.setHidden(dto.hidden());
            byte[] icon= dto.icon()!=null? dto.icon().getBytes():null;
            externalLink.setIcon(icon);
            externalLink.setTitle(dto.title());

            return externalLink;
        }).toList();

        profile.getExternalLinks().addAll(externalLinks);
        Profile savedProfile= profileRepository.save(profile);

        return savedProfile.getExternalLinks().stream().map(link->{
            String iconString=link.getIcon()!=null?new String(link.getIcon()):null;
            return new UpdateExternalLinksResponseDTO(link.getId(),iconString,
                    link.getTitle(),link.getUrl(),link.isHidden());
        }).toList();

    }

    public GetProfileResponseDTO getProfileByUsername(String username) {
        Profile profile = profileRepository.findByUser_Username(username).orElseThrow(EntityNotFoundException::new);
        return GetProfileResponseDTO.fromEntity(profile);

    }

    public UpdateProfileImageResponseDTO updateProfileImage(UpdateProfileImageRequestDTO updateProfileImageRequestDTO, User user) {
        Profile profile = profileRepository.findByUser_Id(user.getId()).orElseThrow(EntityNotFoundException::new);

        profile.setImage(updateProfileImageRequestDTO.image().getBytes());

        Profile savedProfile= profileRepository.save(profile);

        return new UpdateProfileImageResponseDTO(new String(savedProfile.getImage()));

    }

    public UpdateProfileHeaderResponseDTO updateProfileHeader(UpdateProfileHeaderRequestDTO updateProfileHeaderRequestDTO, User user) {
        Profile profile=profileRepository.findByUser_Id(user.getId()).orElseThrow(EntityNotFoundException::new);

        profile.setHeaderTitle(updateProfileHeaderRequestDTO.headerTitle());
        profile.setHeaderDescription(updateProfileHeaderRequestDTO.headerDescription());
        profile.setDisplayName(updateProfileHeaderRequestDTO.displayName());
        Profile savedProfile=profileRepository.save(profile);

        return new UpdateProfileHeaderResponseDTO(savedProfile.getDisplayName(), savedProfile.getHeaderTitle(), savedProfile.getHeaderDescription());


    }

    protected Optional<Profile> getProfileByUserId(Long userId) {

        return profileRepository.findByUser_Id(userId);
    }


}
