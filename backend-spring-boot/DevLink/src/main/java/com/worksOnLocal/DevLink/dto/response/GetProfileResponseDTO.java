package com.worksOnLocal.DevLink.dto.response;

import com.worksOnLocal.DevLink.entity.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

public record GetProfileResponseDTO(
        Long id,
        UserPreviewResponseDTO user,
        String displayName,
        String headerTitle,
        String headerDescription,
        String image,
        List<SocialLinkResponseDTO> socialLinks,
        List<ExternalLinkResponseDTO> externalLinks,
        List<ProjectResponseDTO> projects,
        Instant updatedAt,
        boolean hidden
) {
    public static GetProfileResponseDTO fromEntity(Profile profile){

        List<SocialLinkResponseDTO> socialLinks= profile.getSocialLinks().stream().map(link->new SocialLinkResponseDTO(link.getId(),link.getUrl(),link.getPlatform(),link.isHidden())).toList();
        List<ExternalLinkResponseDTO> externalLinks= profile.getExternalLinks().stream().map(link->{
            String iconString= link.getIcon()!=null ? new String(link.getIcon()) : null;
            return new ExternalLinkResponseDTO(link.getId(),iconString,link.getTitle(),link.getUrl(), link.isHidden());
        }).toList();

        List<ProjectResponseDTO> projects= profile.getProjects().stream().map(project->{
            String imageString = project.getImage() !=null? new String(project.getImage()) : null;
            return new ProjectResponseDTO(project.getId(),imageString,project.getName(),project.getDescription(),project.getDeploymentUrl(),project.getSourceCodeUrl(),project.getTechsUsed(),project.isHidden());
        }).toList();


        return new GetProfileResponseDTO(
                profile.getId(),
                new UserPreviewResponseDTO(profile.getUser().getUsername()),
                profile.getDisplayName(),
                profile.getHeaderTitle(),
                profile.getHeaderDescription(),
                profile.getImage() != null? new String(profile.getImage()):null,
                socialLinks,
                externalLinks,
                projects,
                profile.getUpdatedAt(),
                profile.isHidden()
            );
    }
}

